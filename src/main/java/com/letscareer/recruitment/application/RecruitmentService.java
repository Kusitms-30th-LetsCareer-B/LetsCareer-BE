package com.letscareer.recruitment.application;

import com.letscareer.calendar.application.ScheduleService;
import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.recruitment.domain.Stage;
import com.letscareer.recruitment.domain.StageStatusType;
import com.letscareer.recruitment.domain.repository.RecruitmentRepository;
import com.letscareer.recruitment.domain.repository.StageRepository;
import com.letscareer.recruitment.dto.request.EnrollRecruitmentReq;
import com.letscareer.recruitment.dto.response.*;
import com.letscareer.recruitment.presentation.RecruitmentController;
import com.letscareer.user.domain.User;
import com.letscareer.user.domain.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RecruitmentService {

    private final ScheduleService scheduleService;
    private final UserRepository userRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final StageRepository stageRepository;

    @Transactional
    public void enrollRecruitment(Long userId, EnrollRecruitmentReq request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_USER));
        Recruitment recruitment = recruitmentRepository.save(Recruitment.of(user, request.getCompanyName(), request.getIsFavorite(), request.getTask(), request.getIsRemind(), request.getAnnouncementUrl()));
        Stage savedStage = stageRepository.save(Stage.of(recruitment,"서류", request.getStageStartDate(), request.getStageEndDate(), StageStatusType.PROGRESS,false));
        scheduleService.addSchedule(userId, savedStage, recruitment.getCompanyName());
    }

    @Transactional(readOnly = true)
    public FindRecruitmentRes findRecruitment(Long recruitmentId) {
        try{
            Recruitment recruitment = recruitmentRepository.findRecruitmentWithStagesByAsc(recruitmentId);
            List<FindRecruitmentRes.StageRes> stageResponses = recruitment.getStages().stream()
                    .map(FindRecruitmentRes.StageRes::from)
                    .toList();
            return FindRecruitmentRes.of(recruitment, stageResponses);
        }
        catch(Exception e){
            throw new CustomException(ExceptionContent.NOT_FOUND_RECRUITMENT);
        }

    }

    @Transactional
    public void deleteRecruitment(Long recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_RECRUITMENT));
        recruitmentRepository.delete(recruitment);
    }

    @Transactional(readOnly = true)
    public GetRecruitmentsStatusRes getRecruitmentsStatus(Long userId) {
        List<Recruitment> recruitments = recruitmentRepository.findAllByUserId(userId);
        int total=recruitments.size();
        int progress=0, passed=0, failed=0;

        for (Recruitment recruitment : recruitments) {
            List<Stage> stages = stageRepository.findAllByRecruitmentIdOrderByEndDateAsc(recruitment.getId());
            DetermineRecruitmentStatusRes statusRes = determineRecruitmentStatus(stages);
            switch (statusRes.getStatus()){
                case PROGRESS -> ++progress;
                case PASSED -> {
                    if (statusRes.getIsFinal()) {
                        ++passed;
                    }
                    else{
                        ++progress;
                    }
                }
                case FAILED -> ++failed;
            }
        }
        return GetRecruitmentsStatusRes.of(total, progress, passed, failed);
    }

    private DetermineRecruitmentStatusRes determineRecruitmentStatus(List<Stage> stages){
        LocalDate today = LocalDate.now();

        List<Stage> prevFilteredStages = stages.stream().filter(stage-> stage.getEndDate().isBefore(today)).toList();
        List<Stage> nextFilteredStages = stages.stream().filter(stage-> !stage.getEndDate().isBefore(today)).toList();

        // 첫 번째 조건: FAILED 상태의 stage가 있는지 확인
        for (Stage stage : prevFilteredStages) {
            if (stage.getStatus().equals(StageStatusType.FAILED)){
                return DetermineRecruitmentStatusRes.from(stage);
            }
        }
        // 두 번째 조건: 과거에 PASSED 상태이며 isFinal이 true인 stage가 있는지 확인
        for (Stage stage : prevFilteredStages) {
            if (stage.getStatus().equals(StageStatusType.PASSED) && stage.getIsFinal()) {
                return DetermineRecruitmentStatusRes.from(stage);
            }
        }

        // 필터링 후 아무런 stage도 남지 않은 경우 (모두 과거에 해당하는 경우)
        if (nextFilteredStages.isEmpty()) {
            // 원래의 stages에서 가장 마지막 stage를 가져옵니다.
            return DetermineRecruitmentStatusRes.from(stages.get(stages.size() - 1));
        }

        // 세 번째 조건: 이후 남은 단계 중 가장 마감일이 가까운 일정를 PROGRESS 상태로 반환
        return DetermineRecruitmentStatusRes.from(nextFilteredStages.get(0));
    }

    @Transactional(readOnly = true)
    public FindAllRecruitmentsRes findAllRecruitments(Long userId) {
        List<Recruitment> recruitments = recruitmentRepository.findAllByUserId(userId);
        LocalDate today = LocalDate.now();

        List<FindAllRecruitmentsRes.RecruitmentInfo> recruitmentInfos = recruitments.stream()
                .map(recruitment -> {
                    List<Stage> stages = stageRepository.findAllByRecruitmentIdOrderByEndDateAsc(recruitment.getId());
                    List<Stage> nextFilteredStages = stages
                            .stream()
                            .filter(stage -> !stage.getEndDate().isBefore(today))
                            .toList();

                    // 빈 리스트일 경우 null을 반환하여 후속 단계에서 제외될 수 있게 함
                    if (nextFilteredStages.isEmpty()) {
                        return null; // 필터링을 통해 제거할 수 있게 null 반환
                    } else {
                        DetermineRecruitmentStatusRes recruitmentStatus = DetermineRecruitmentStatusRes.from(nextFilteredStages.get(0));
                        return FindAllRecruitmentsRes.RecruitmentInfo.of(
                                recruitment,
                                recruitmentStatus.getStageName(),
                                recruitmentStatus.getStatus(),
                                recruitmentStatus.getEndDate(),
                                recruitmentStatus.getDaysUntilFinal()
                        );
                    }
                })
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(FindAllRecruitmentsRes.RecruitmentInfo::getIsFavorite).reversed()  // isFavorite이 true인 것을 앞으로
                        .thenComparing(FindAllRecruitmentsRes.RecruitmentInfo::getDaysUntilEnd))  // 며칠 남았는지 오름차순으로 정렬
                .limit(6)
                .toList();

        return FindAllRecruitmentsRes.of(recruitmentInfos);
    }

    @Transactional(readOnly = true)
    public FindAllRecruitmentsByTypeRes findRecruitmentsByType(String type, Long userId) {
        List<Recruitment> recruitments = recruitmentRepository.findAllByUserId(userId);

        List<FindAllRecruitmentsByTypeRes.RecruitmentInfo> recruitmentInfos = recruitments.stream()
                .filter(recruitment -> {
                    List<Stage> stages = stageRepository.findAllByRecruitmentIdOrderByEndDateAsc(recruitment.getId());
                    DetermineRecruitmentStatusRes statusRes = determineRecruitmentStatus(stages);

                    // type이 PROGRESS일 경우,
                    if (type.equals("progress")) {
                        return statusRes.getStatus().equals(StageStatusType.PROGRESS) || (statusRes.getStatus().equals(StageStatusType.PASSED) && !statusRes.getIsFinal());
                    }
                    // type이 CONSEQUENCE일 경우, FAILED 또는 PASSED 상태를 필터링
                    else if (type.equals("consequence")) {
                        return statusRes.getStatus().equals(StageStatusType.FAILED) || (statusRes.getStatus().equals(StageStatusType.PASSED) && statusRes.getIsFinal());
                    }
                    return false;
                })
                .map(recruitment -> {
                            List<Stage> stages = stageRepository.findAllByRecruitmentIdOrderByEndDateAsc(recruitment.getId());
                            DetermineRecruitmentStatusRes recruitmentStatus = determineRecruitmentStatus(stages);

                            return FindAllRecruitmentsByTypeRes.RecruitmentInfo.of(
                                    recruitment,
                                    recruitmentStatus.getStageName(),
                                    recruitmentStatus.getStatus(),
                                    recruitmentStatus.getEndDate(),
                                    recruitmentStatus.getDaysUntilFinal()
                            );
                        })
                .sorted(Comparator.comparing(FindAllRecruitmentsByTypeRes.RecruitmentInfo::getIsFavorite).reversed()  // isFavorite이 true인 것을 앞으로
                        .thenComparing(FindAllRecruitmentsByTypeRes.RecruitmentInfo::getDaysUntilEnd))  // 며칠 남았는지 오름차순으로 정렬
                .toList();

        return FindAllRecruitmentsByTypeRes.of(recruitmentInfos);
    }
}

package com.letscareer.recruitment.application;

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

@Service
@RequiredArgsConstructor
public class RecruitmentService {

    private final UserRepository userRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final StageRepository stageRepository;

    @PostConstruct
    public void init() {
        userRepository.save(User.builder()
                .name("하이")
                .email("jh981109@naver.com")
                .build());
        userRepository.save(User.builder()
                .name("하이2")
                .email("wnsgud@naver.com")
                .build());
    }

    @Transactional
    public void enrollRecruitment(Long userId, EnrollRecruitmentReq request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_USER));
        Recruitment recruitment = recruitmentRepository.save(Recruitment.of(user, request.getCompanyName(), request.getIsFavorite(), request.getTask(), request.getIsRemind(), request.getAnnouncementUrl()));
        stageRepository.save(Stage.of(recruitment,"서류", request.getStageStartDate(), request.getStageEndDate(), StageStatusType.PROGRESS,false));
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
            switch (determineRecruitmentStatus(stages).getStatus()){
                case PROGRESS -> ++progress;
                case PASSED -> ++passed;
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
            if (stage.getStatus() == StageStatusType.PASSED && stage.getIsFinal()) {
                return DetermineRecruitmentStatusRes.from(stage);
            }
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
                    DetermineRecruitmentStatusRes recruitmentStatus = determineRecruitmentStatus(stages);

                    // LocalDate의 toEpochDay()를 사용하여 endDate와 현재 날짜의 차이 계산
                    long daysUntilEnd = recruitmentStatus.getEndDate().toEpochDay() - today.toEpochDay();

                    return FindAllRecruitmentsRes.RecruitmentInfo.of(
                            recruitment,
                            recruitmentStatus.getStageName(),
                            recruitmentStatus.getStatus(),
                            recruitmentStatus.getEndDate(),
                            daysUntilEnd
                    );
                })
                .sorted(Comparator.comparing(FindAllRecruitmentsRes.RecruitmentInfo::getIsFavorite).reversed()  // isFavorite이 true인 것을 앞으로
                        .thenComparing(FindAllRecruitmentsRes.RecruitmentInfo::getDaysUntilEnd))  // 며칠 남았는지 오름차순으로 정렬
                .toList();

        return FindAllRecruitmentsRes.of(recruitmentInfos);
    }

//    @Transactional(readOnly = true)
//    public void findRecruitmentsByType(String type, Long userId) {
//        List<Recruitment> recruitments = recruitmentRepository.findAllByUserId(userId);
//        LocalDate now = LocalDate.now();  // LocalDate 사용
//
//        List<FindAllRecruitmentsByTypeRes.RecruitmentInfo> recruitmentInfos = recruitments.stream()
//                .map(recruitment -> {
//                    List<Stage> stages = stageRepository.findAllByRecruitmentIdOrderByEndDateDesc(recruitment.getId());
//                    DetermineRecruitmentStatusRes recruitmentStatus = determineRecruitmentStatus(stages);
//
//                    // LocalDate의 toEpochDay()를 사용하여 endDate와 현재 날짜의 차이 계산
//                    long daysUntilEnd = recruitmentStatus.getEndDate().toEpochDay() - now.toEpochDay();
//
//                    return FindAllRecruitmentsByTypeRes.RecruitmentInfo.of(
//                            recruitment,
//                            recruitmentStatus.getStageName(),
//                            recruitmentStatus.getStatus(),
//                            recruitmentStatus.getEndDate(),
//                            daysUntilEnd,
//                            recruitmentStatus.getIsFinal()
//                    );
//                })
//                .filter(info -> !info.getStatus().equals("FAILED"))  // status가 FAILED가 아닌 것 필터링
//                .filter(info -> !(info.getIsFinal() && info.getStatus().equals("PASSED")))  // isFinal이 true이면서 status가 PASSED가 아닌 것 필터링
//                .sorted(Comparator.comparing(FindAllRecruitmentsByTypeRes.RecruitmentInfo::getIsFavorite).reversed()  // isFavorite이 true인 것을 앞으로
//                        .thenComparing(FindAllRecruitmentsByTypeRes.RecruitmentInfo::getDaysUntilEnd))  // 며칠 남았는지 오름차순으로 정렬
//                .toList();
//    }
}

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
import com.letscareer.user.domain.User;
import com.letscareer.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    private static final int RECRUITMENT_PAGE_SIZE = 6;

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
            Recruitment recruitment = recruitmentRepository.findRecruitmentWithStagesByAsc(recruitmentId);
            if (recruitment == null) {
                throw new CustomException(ExceptionContent.NOT_FOUND_RECRUITMENT);
            }

            List<FindRecruitmentRes.StageRes> stageResponses = recruitment.getStages().stream()
                    .map(FindRecruitmentRes.StageRes::from)
                    .toList();
            List<Stage> stages = stageRepository.findAllByRecruitmentIdOrderByEndDateAsc(recruitment.getId());
            if (stages.isEmpty()) {
                throw new CustomException(ExceptionContent.NOT_FOUND_RECRUITMENT);


            }
            DetermineRecruitmentStatusRes statusRes = determineRecruitmentStatus(stages);

            return FindRecruitmentRes.of(recruitment, stageResponses, statusRes.getStageName(), statusRes.getStatus(), statusRes.getDaysUntilFinal());


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
        int document=0, interview=0, other=0;

        for (Recruitment recruitment : recruitments) {
            List<Stage> stages = stageRepository.findAllByRecruitmentIdOrderByEndDateAsc(recruitment.getId());
            DetermineRecruitmentStatusRes statusRes = determineRecruitmentStatus(stages);
            if (statusRes.getStageName().equals("서류")){
                document++;
            }
            else if (statusRes.getStageName().equals("면접")){
                interview++;
            }
            else{
                other++;
            }

        }
        return GetRecruitmentsStatusRes.of(total, document, interview, other);
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
            if (!stages.isEmpty()) {
                return DetermineRecruitmentStatusRes.from(stages.get(stages.size() - 1));
            } else {
                // 만약 stages 리스트가 비어 있다면, 적절한 예외를 던지거나 기본값을 반환
                throw new CustomException(ExceptionContent.NO_VALID_STAGE_FOUND);
            }
        }

        // 세 번째 조건: 이후 남은 단계 중 가장 마감일이 가까운 일정를 PROGRESS 상태로 반환
        return DetermineRecruitmentStatusRes.from(nextFilteredStages.get(0));
    }

    @Transactional(readOnly = true)
    public FindAllRecruitmentsRes findAllRecruitments(Long userId, Long page) {
        long offset = (page - 1) * RECRUITMENT_PAGE_SIZE;
        long limit = RECRUITMENT_PAGE_SIZE;
        LocalDate today = LocalDate.now();

        // QueryDSL로 필터링된 채용 정보 가져오기
        List<Recruitment> recruitments = recruitmentRepository.findRecruitmentsWithUpcomingStages(userId, today, offset, limit);

        // 전체 데이터 개수 계산 (총 페이지 수 및 총 개수 계산에 사용)
        long totalRecruitmentsCount = recruitmentRepository.countRecruitmentsWithUpcomingStages(userId, today);
        long totalPages = (totalRecruitmentsCount + RECRUITMENT_PAGE_SIZE - 1) / RECRUITMENT_PAGE_SIZE;


        // recruitments 리스트를 RecruitmentInfo로 변환 및 정렬
        List<FindAllRecruitmentsRes.RecruitmentInfo> recruitmentInfos = recruitments.stream()
                .map(recruitment -> {
                    List<Stage> stages = stageRepository.findAllStagesByRecruitmentId(recruitment.getId());

                    // 필터링 후 첫 번째 단계로 상태 추출
                    DetermineRecruitmentStatusRes recruitmentStatus = DetermineRecruitmentStatusRes.from(stages.get(0));
                    return FindAllRecruitmentsRes.RecruitmentInfo.of(
                            recruitment,
                            recruitmentStatus.getStageName(),
                            recruitmentStatus.getStatus(),
                            recruitmentStatus.getEndDate(),
                            recruitmentStatus.getDaysUntilFinal()
                    );
                })
                .sorted(Comparator.comparing(FindAllRecruitmentsRes.RecruitmentInfo::getIsFavorite).reversed()  // isFavorite이 true인 것을 먼저
                        .thenComparing(FindAllRecruitmentsRes.RecruitmentInfo::getDaysUntilEnd))  // 남은 일수 오름차순으로 정렬
                .toList();

        return FindAllRecruitmentsRes.of(totalPages, page, totalRecruitmentsCount, recruitmentInfos);
    }

    @Transactional(readOnly = true)
    public FindAllRecruitmentsByTypeRes findRecruitmentsByType(String type, Long userId, Long page) {
        long offset = (page - 1) * RECRUITMENT_PAGE_SIZE;
        long limit = RECRUITMENT_PAGE_SIZE;
        LocalDate today = LocalDate.now();

        // 모든 Recruitment를 가져옴
        List<Recruitment> recruitments = recruitmentRepository.findAllByUserId(userId);

        // 각 Recruitment의 상태를 계산하고 필터링
        List<FindAllRecruitmentsByTypeRes.RecruitmentInfo> filteredRecruitments = recruitments.stream()
                .map(recruitment -> {
                    List<Stage> stages = stageRepository.findAllByRecruitmentIdOrderByEndDateAsc(recruitment.getId());
                    DetermineRecruitmentStatusRes recruitmentStatus = determineRecruitmentStatus(stages);

                    return FindAllRecruitmentsByTypeRes.RecruitmentInfo.of(
                            recruitment,
                            recruitmentStatus.getStageName(),
                            recruitmentStatus.getStatus(),
                            recruitmentStatus.getEndDate(),
                            recruitmentStatus.getDaysUntilFinal(),
                            recruitmentStatus.getIsFinal()
                    );
                })
                .filter(recruitmentInfo -> {
                    // progress 필터링: status가 PROGRESS이거나 PASSED && isFinal이 false
                    if (type.equalsIgnoreCase("progress")) {
                        return recruitmentInfo.getStatus() == StageStatusType.PROGRESS ||
                                (recruitmentInfo.getStatus() == StageStatusType.PASSED && !recruitmentInfo.getIsFinal());
                    }
                    // consequence 필터링: status가 FAILED이거나 PASSED && isFinal이 true
                    else if (type.equalsIgnoreCase("consequence")) {
                        return recruitmentInfo.getStatus() == StageStatusType.FAILED ||
                                (recruitmentInfo.getStatus() == StageStatusType.PASSED && recruitmentInfo.getIsFinal());
                    }
                    return false;
                })
                .sorted(Comparator.comparing(FindAllRecruitmentsByTypeRes.RecruitmentInfo::getIsFavorite).reversed()
                        .thenComparing(FindAllRecruitmentsByTypeRes.RecruitmentInfo::getDaysUntilEnd))
                .toList();

        // 필터링된 전체 데이터 개수
        long totalRecruitmentsCount = filteredRecruitments.size();
        long totalPages = (totalRecruitmentsCount + RECRUITMENT_PAGE_SIZE - 1) / RECRUITMENT_PAGE_SIZE;

        // 페이지네이션 적용 (6개씩 잘라서 반환)
        List<FindAllRecruitmentsByTypeRes.RecruitmentInfo> paginatedRecruitments = filteredRecruitments.stream()
                .skip(offset)
                .limit(limit)
                .toList();

        return FindAllRecruitmentsByTypeRes.of(totalPages, page, totalRecruitmentsCount, paginatedRecruitments);
    }

    @Transactional
    public void modifyRecruitmentFavorite(Long recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_RECRUITMENT));
        recruitment.modifyFavorite();
    }

    @Transactional(readOnly = true)
    public List<GetRecruitmentsNameRes> getRecruitmentsName(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_USER));
        List<Recruitment> recruitments = recruitmentRepository.findAllByUser(user);
        return recruitments.stream().map(GetRecruitmentsNameRes::from).toList();
    }
}

package com.letscareer.calendar.application;

import com.letscareer.calendar.domain.Schedule;
import com.letscareer.calendar.domain.ScheduleFilter;
import com.letscareer.calendar.domain.repository.ScheduleRepository;
import com.letscareer.calendar.dto.response.ScheduleResponse;
import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import com.letscareer.recruitment.domain.Stage;
import com.letscareer.user.domain.User;
import com.letscareer.user.domain.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    /**
     * 월에 해당하는 스케줄을 가져오는 메소드
     *
     * @param userId the user id
     * @param year   the year
     * @param month  the month
     * @return the schedule for month
     */
    @Transactional(readOnly = true)
    public List<ScheduleResponse> getScheduleForMonth(Long userId, int year, int month) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_USER));

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        return scheduleRepository.findAllByUserIdAndDateBetween(user.getId(), startDate, endDate).stream()
                .map(ScheduleResponse::new)
                .toList();
    }

    /**
     * 스케줄(채용 일정)을 추가하는 메소드
     *
     * @param userId the user id
     * @param stage  the stage
     * @return the void
     */
    @Transactional// Todo: 채용전형 추가 로직에 추가해야됨
    public void addSchedule(Long userId, Stage stage, String companyName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_USER));
        Schedule schedule;
        switch (stage.getStageName()) {
            case "서류" -> {
                addDocumentSchedule(user, stage, companyName);
                return;
            }
            case "필기" -> schedule = Schedule.of(user, stage, stage.getEndDate(), ScheduleFilter.WRITTEN, companyName);
            case "면접" -> schedule = Schedule.of(user, stage, stage.getEndDate(), ScheduleFilter.INTERVIEW, companyName);
            default -> schedule = Schedule.of(user, stage, stage.getEndDate(), ScheduleFilter.OTHER, companyName);
        }
        scheduleRepository.save(schedule);
    }

    @Transactional
    public void addDocumentSchedule(User user, Stage stage, String companyName) {
        Schedule startSchedule = Schedule.of(user, stage, stage.getStartDate(), ScheduleFilter.START, companyName);
        Schedule finishSchedule = Schedule.of(user, stage, stage.getEndDate(), ScheduleFilter.FINISH, companyName);
        scheduleRepository.save(startSchedule);
        scheduleRepository.save(finishSchedule);
    }

    // Todo: 채용 전형 삭제 로직에 추가해야됨
    @Transactional
    public void delete(Long stageId) {
        scheduleRepository.deleteByStageId(stageId);
    }

}

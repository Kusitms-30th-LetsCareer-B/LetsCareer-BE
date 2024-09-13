package com.letscareer.calendar.application;

import com.letscareer.calendar.domain.Schedule;
import com.letscareer.calendar.domain.ScheduleFilter;
import com.letscareer.calendar.domain.repository.ScheduleRepository;
import com.letscareer.calendar.domain.repository.ScheduleRepositoryCustom;
import com.letscareer.calendar.dto.response.ScheduleContentResponse;
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
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleRepositoryCustom scheduleRepositoryCustom;
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

        return scheduleRepositoryCustom.findAllByUserIdAndDateBetweenWithStage(user.getId(), startDate, endDate).stream()
                .map(ScheduleResponse::new)
                .toList();
    }

    /**
     * 특정 날짜에 해당하는 스케줄을 가져오는 메소드
     *
     * @param userId the user id
     * @param date   the date
     * @return the schedule for the date
     */
    @Transactional(readOnly = true)
    public List<ScheduleContentResponse> getScheduleForDate(Long userId, LocalDate date) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_USER));

        List<Schedule> schedules = scheduleRepository.findAllByUserIdAndDate(userId, date);

        return schedules.stream()
            .map(schedule -> {
                String additionalInfo = null;
                String stageName = schedule.getStage().getStageName();

                // 서류 단계인 경우 filter에 따라 '시작' 또는 '마감'을 추가
                if ("서류".equals(stageName)) {
                    if (ScheduleFilter.START.equals(schedule.getFilter())) {
                        additionalInfo = "시작";
                    } else if (ScheduleFilter.FINISH.equals(schedule.getFilter())) {
                        additionalInfo = "마감";
                    }
                }

                return new ScheduleContentResponse(schedule.getId(), schedule.getCompanyName(), stageName, additionalInfo);
            })
            .collect(Collectors.toList());
    }

    /**
     * 스케줄(채용 일정)을 추가하는 메소드
     *
     * @param userId the user id
     * @param stage  the stage
     * @return the void
     */
    @Transactional
    public void addSchedule(Long userId, Stage stage, String companyName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_USER));
        Schedule schedule;
        switch (stage.getStageName()) {
            case "서류" -> {
                addDocumentSchedule(user, stage, companyName);
                return;
            }
            case "면접" -> schedule = Schedule.of(user, stage, stage.getEndDate(), ScheduleFilter.INTERVIEW, companyName);
            default -> schedule = Schedule.of(user, stage, stage.getEndDate(), ScheduleFilter.OTHER, companyName);
        }
        scheduleRepository.save(schedule);
    }

    @Transactional
    public void addDocumentSchedule(User user, Stage stage, String companyName) {
        if(stage.getStartDate() != null){
            Schedule startSchedule = Schedule.of(user, stage, stage.getStartDate(), ScheduleFilter.START, companyName);
            scheduleRepository.save(startSchedule);
        }
        Schedule finishSchedule = Schedule.of(user, stage, stage.getEndDate(), ScheduleFilter.FINISH, companyName);
        scheduleRepository.save(finishSchedule);
    }

    @Transactional
    public void updateScheduleDate(Long stageId, LocalDate newDate) {
        List<Schedule> schedules = scheduleRepository.findAllByStageId(stageId);

        if (schedules.isEmpty()) {
            throw new CustomException(ExceptionContent.NOT_FOUND_SCHEDULE);
        }

        for (Schedule schedule : schedules) {
            schedule.updateDate(newDate);
        }
    }


    @Transactional
    public void delete(Long stageId) {
        scheduleRepository.deleteByStageId(stageId);
    }

}

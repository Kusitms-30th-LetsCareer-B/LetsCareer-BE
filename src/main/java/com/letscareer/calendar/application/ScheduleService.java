package com.letscareer.calendar.application;

import com.letscareer.calendar.domain.Schedule;
import com.letscareer.calendar.domain.ScheduleFilter;
import com.letscareer.calendar.domain.repository.ScheduleRepository;
import com.letscareer.calendar.dto.request.PersonalScheduleRequest;
import com.letscareer.calendar.dto.response.ScheduleResponse;
import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
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
     * 개인 일정을 추가하는 메소드
     *
     * @param request the request
     * @return the added schedule response
     */
    @Transactional
    public ScheduleResponse addPersonalSchedule(Long userId, PersonalScheduleRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_USER));

        Schedule schedule = Schedule.of(request, user, ScheduleFilter.PERSONAL);

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponse(savedSchedule);
    }

}

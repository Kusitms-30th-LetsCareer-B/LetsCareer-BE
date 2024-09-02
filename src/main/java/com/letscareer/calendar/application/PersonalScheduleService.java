package com.letscareer.calendar.application;

import com.letscareer.calendar.domain.PersonalSchedule;
import com.letscareer.calendar.domain.Schedule;
import com.letscareer.calendar.domain.ScheduleFilter;
import com.letscareer.calendar.domain.repository.PersonalScheduleRepository;
import com.letscareer.calendar.domain.repository.ScheduleRepository;
import com.letscareer.calendar.dto.request.PersonalScheduleRequest;
import com.letscareer.calendar.dto.response.PersonalScheduleResponse;
import com.letscareer.calendar.dto.response.ScheduleResponse;
import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import com.letscareer.user.domain.User;
import com.letscareer.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class PersonalScheduleService {
    private final PersonalScheduleRepository personalScheduleRepository;
    private final UserRepository userRepository;

    /**
     * 개인 일정을 추가하는 메소드
     *
     * @param request the request
     * @return the added schedule response
     */
    @Transactional
    public Void addPersonalSchedule(Long userId, PersonalScheduleRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_USER));

        PersonalSchedule personalSchedule = PersonalSchedule.of(user, request);

        personalScheduleRepository.save(personalSchedule);
        return null;
    }

    /**
     * 월에 해당하는 개인 일정을 가져오는 메소드
     *
     * @param userId the user id
     * @param year   the year
     * @param month  the month
     * @return the schedule for month
     */
    @Transactional(readOnly = true)
    public List<PersonalScheduleResponse> getPersonalScheduleForMonth(Long userId, int year, int month) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_USER));

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        return personalScheduleRepository.findAllByUserIdAndDateBetween(user.getId(), startDate, endDate).stream()
                .map(PersonalScheduleResponse::new)
                .toList();
    }

}

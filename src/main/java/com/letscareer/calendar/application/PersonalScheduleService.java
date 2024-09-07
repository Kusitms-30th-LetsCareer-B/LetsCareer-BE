package com.letscareer.calendar.application;

import com.letscareer.calendar.domain.PersonalSchedule;
import com.letscareer.calendar.domain.repository.PersonalScheduleRepository;
import com.letscareer.calendar.dto.request.PersonalScheduleRequest;
import com.letscareer.calendar.dto.response.PersonalScheduleResponse;
import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonalScheduleService {
    private final PersonalScheduleRepository personalScheduleRepository;

    // 개인 일정 추가
    public void addPersonalSchedule(Long userId, PersonalScheduleRequest request) {
        // 일정 추가 로직
    }

    // 개인 일정 조회
    public List<PersonalScheduleResponse> getPersonalScheduleForMonth(Long userId, int year, int month) {
        // 일정 조회 로직
    }

    // 개인 일정 수정
    public void updatePersonalSchedule(Long personalScheduleId, PersonalScheduleRequest request) {
        PersonalSchedule personalSchedule = personalScheduleRepository.findById(personalScheduleId)
            .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_RECRUITMENT));

        personalSchedule.update(request);
        personalScheduleRepository.save(personalSchedule);
    }

    // 개인 일정 삭제
    public void deletePersonalSchedule(Long personalScheduleId) {
        PersonalSchedule personalSchedule = personalScheduleRepository.findById(personalScheduleId)
            .orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_RECRUITMENT));

        personalScheduleRepository.delete(personalSchedule);
    }
}

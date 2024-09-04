package com.letscareer.todo.application;

import com.letscareer.global.exception.CustomException;
import com.letscareer.global.exception.ExceptionContent;
import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.recruitment.domain.repository.RecruitmentRepository;
import com.letscareer.todo.domain.Routine;
import com.letscareer.todo.domain.Todo;
import com.letscareer.todo.domain.repository.RoutineRepository;
import com.letscareer.todo.domain.repository.TodoRepository;
import com.letscareer.todo.dto.request.CreateRoutineReq;
import com.letscareer.user.domain.User;
import com.letscareer.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RoutineService {

    private final RoutineRepository routineRepository;
    private final UserRepository userRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final TodoRepository todoRepository;

    @Transactional
    public void createRoutine(Long userId, Long recruitmentId, CreateRoutineReq request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_USER));
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_RECRUITMENT));


        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();

        Routine routine = routineRepository.save(Routine.of(user, recruitment, request.getContent(), startDate, endDate));
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            todoRepository.save(Todo.ofRoutine(user, recruitment, routine, date, request.getContent(), false));
        }

    }

    @Transactional
    public void deleteRoutine(Long routineId) {
        Routine routine = routineRepository.findById(routineId).orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_ROUTINE));
        routineRepository.delete(routine);
    }
}

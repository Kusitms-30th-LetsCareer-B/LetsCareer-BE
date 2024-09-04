package com.letscareer.todo.domain.repository;

import com.letscareer.recruitment.domain.Recruitment;
import com.letscareer.todo.domain.Todo;
import com.letscareer.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findAllByUserAndDate(User user, LocalDate date);

    List<Todo> findAllByRecruitmentAndDate(Recruitment recruitment, LocalDate date);
}

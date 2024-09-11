package com.letscareer.alert.domain.repository;

import com.letscareer.alert.domain.Alert;
import com.letscareer.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    // endDate가 오늘 날짜와 같거나 이후인 알림만 조회
    List<Alert> findAllByUserAndEndDateGreaterThanEqualOrderByEndDateAsc(User user, LocalDate today);
}

package com.letscareer.alert.domain.repository;

import com.letscareer.alert.domain.Alert;
import com.letscareer.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    List<Alert> findAllByUserOrderByIdDesc(User user);
}

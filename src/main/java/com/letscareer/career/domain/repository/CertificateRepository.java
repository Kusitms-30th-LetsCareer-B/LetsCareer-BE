package com.letscareer.career.domain.repository;

import com.letscareer.career.domain.Certificate;
import com.letscareer.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
	List<Certificate> findByUser(User user);
}
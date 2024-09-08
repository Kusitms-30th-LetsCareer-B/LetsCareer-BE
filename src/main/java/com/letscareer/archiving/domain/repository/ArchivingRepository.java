package com.letscareer.archiving.domain.repository;

import com.letscareer.archiving.domain.Archiving;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchivingRepository extends JpaRepository<Archiving, Long> {
}

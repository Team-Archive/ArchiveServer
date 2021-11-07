package com.depromeet.archive.domain.archive.entity;

import com.depromeet.archive.domain.archive.entity.Archive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveRepository extends JpaRepository<Archive, Long> {
}
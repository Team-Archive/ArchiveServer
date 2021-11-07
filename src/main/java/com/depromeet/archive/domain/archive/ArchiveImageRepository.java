package com.depromeet.archive.domain.archive;

import com.depromeet.archive.domain.archive.entity.ArchiveImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveImageRepository extends JpaRepository<ArchiveImage, Long> {
}
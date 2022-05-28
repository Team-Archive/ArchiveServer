package site.archive.domain.archive;

import org.springframework.data.jpa.repository.JpaRepository;
import site.archive.domain.archive.entity.ArchiveImage;

public interface ArchiveImageRepository extends JpaRepository<ArchiveImage, Long> {
}
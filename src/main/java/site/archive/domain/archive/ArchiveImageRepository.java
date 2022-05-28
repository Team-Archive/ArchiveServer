package site.archive.domain.archive;

import site.archive.domain.archive.entity.ArchiveImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveImageRepository extends JpaRepository<ArchiveImage, Long> {
}
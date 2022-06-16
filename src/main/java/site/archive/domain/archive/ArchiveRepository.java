package site.archive.domain.archive;

import org.springframework.data.jpa.repository.JpaRepository;
import site.archive.domain.archive.entity.Archive;

import java.util.List;

public interface ArchiveRepository extends JpaRepository<Archive, Long> {

    List<Archive> findAllByAuthorUserId(Long authorId);

    long countArchiveByAuthorUserId(Long authorId);

}
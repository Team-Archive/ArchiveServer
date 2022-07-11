package site.archive.domain.archive;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import site.archive.domain.archive.entity.Archive;

import java.util.List;

public interface ArchiveRepository extends JpaRepository<Archive, Long>, ArchiveCustomRepository {

    List<Archive> findAllByAuthorId(Long authorId);

    @EntityGraph(attributePaths = {"author", "likes"})
    List<Archive> findDistinctByIdIn(List<Long> archiveIds);

    long countArchiveByAuthorId(Long authorId);

}
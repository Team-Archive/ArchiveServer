package site.archive.domain.archive;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import site.archive.domain.archive.custom.ArchiveCustomRepository;

import java.util.List;

public interface ArchiveRepository extends JpaRepository<Archive, Long>, ArchiveCustomRepository {

    List<Archive> findAllByAuthorId(Long authorId);

    long countArchiveByAuthorId(Long authorId);

    @EntityGraph(attributePaths = {"author", "likes"})
    List<Archive> findAllByIdIn(List<Long> archiveIds);

}
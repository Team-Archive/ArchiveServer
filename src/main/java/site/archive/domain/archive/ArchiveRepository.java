package site.archive.domain.archive;

import site.archive.domain.archive.entity.Archive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArchiveRepository extends JpaRepository<Archive, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update Archive a set a.isDeleted=true where a.id = :id")
    void deleteById(Long id);

    List<Archive> findAllByAuthorId(Long authorId);

    long countArchiveByAuthorId(Long authorId);

}
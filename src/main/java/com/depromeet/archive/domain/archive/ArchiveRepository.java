package com.depromeet.archive.domain.archive;

import com.depromeet.archive.domain.archive.entity.Archive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ArchiveRepository extends JpaRepository<Archive, Long> {

    @Modifying
    @Query("update Archive a set a.isDeleted=true where a.id = :id")
    void deleteById(Long id);
}
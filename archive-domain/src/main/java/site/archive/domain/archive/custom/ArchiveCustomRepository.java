package site.archive.domain.archive.custom;

import site.archive.domain.archive.Archive;

import java.util.List;

public interface ArchiveCustomRepository {

    List<Archive> findFirstPageByAuthorId(Long authorId, ArchivePageable pageable, int pageElementSize);

    List<Archive> findNextPageByAuthorId(Long authorId, ArchivePageable pageable, int pageElementSize);

    List<Archive> findFirstPageOnlyPublic(ArchivePageable pageable, int pageElementSize);

    List<Archive> findNextPageOnlyPublic(ArchivePageable pageable, int pageElementSize);

    List<Archive> findByIdInWithLike(List<Long> archiveIds, ArchivePageable pageable);

    long countArchiveOfCurrentMonthByAuthorId(Long authorId);

}

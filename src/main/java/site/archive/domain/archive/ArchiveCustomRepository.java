package site.archive.domain.archive;

import site.archive.domain.archive.entity.Archive;

import java.util.List;

public interface ArchiveCustomRepository {

    List<Archive> findFirstPageOnlyPublic(ArchivePageable pageable, int pageElementSize);

    List<Archive> findNextPageOnlyPublic(ArchivePageable pageable, int pageElementSize);

}

package site.archive.domain.archive;

import site.archive.domain.archive.entity.Archive;

import java.util.List;

public interface ArchiveCustomRepository {

    List<Archive> findFirstPage(ArchivePageable pageable, int pageElementSize);

    List<Archive> findNextPage(ArchivePageable pageable, int pageElementSize);

}

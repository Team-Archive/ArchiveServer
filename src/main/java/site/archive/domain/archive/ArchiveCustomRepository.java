package site.archive.domain.archive;

import site.archive.domain.archive.entity.Archive;

import java.util.List;

public interface ArchiveCustomRepository {

    List<Archive> findFirstPage(ArchivePageable archivePageable, int pageElementSize);

    List<Archive> findNextPage(ArchivePageable archivePageable, int pageElementSize);

}

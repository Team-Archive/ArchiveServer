package site.archive.domain.archive;

import site.archive.domain.archive.entity.Archive;
import site.archive.domain.archive.entity.Emotion;

import java.util.List;

public interface ArchiveCustomRepository {

    List<Archive> findFirstPage(ArchiveCommunityTimeSortType timeSortType,
                                Emotion emotion,
                                int pageElementSize);

    List<Archive> findNextPage(ArchiveCommunityTimeSortType timeSortType,
                               Emotion emotion,
                               Long lastSeenArchiveDateMilli,
                               Long lastSeenArchiveId,
                               int pageElementSize);

}

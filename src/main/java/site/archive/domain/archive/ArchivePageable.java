package site.archive.domain.archive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.archive.domain.archive.entity.Emotion;

@AllArgsConstructor
@Getter
public class ArchivePageable {

    private ArchiveCommunityTimeSortType sortType;
    private Emotion emotion;
    private Long lastSeenArchiveDateMilli;
    private Long lastSeenArchiveId;

    public boolean isFirstPage() {
        return lastSeenArchiveDateMilli == null || lastSeenArchiveId == null;
    }

}

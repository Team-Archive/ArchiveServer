package site.archive.domain.archive.custom;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import site.archive.domain.archive.Emotion;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class ArchivePageable {

    private ArchiveCommunityTimeSortType sortType;
    private Emotion emotion;
    private Long lastArchiveDateTime;
    private Long lastArchiveId;

    public boolean isRequestFirstPage() {
        return lastArchiveDateTime == null || lastArchiveId == null;
    }

}

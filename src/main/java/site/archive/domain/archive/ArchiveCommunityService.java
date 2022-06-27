package site.archive.domain.archive;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.archive.api.v2.dto.ArchiveCommunityResponseDto;
import site.archive.domain.archive.entity.Emotion;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArchiveCommunityService {

    private static final int ARCHIVE_COMMUNITY_PAGE_ELEMENT_SIZE = 15;

    private final ArchiveRepository archiveRepository;

    public List<ArchiveCommunityResponseDto> archiveCommunityFirstPage(ArchiveCommunityTimeSortType timeSortType,
                                                                       Emotion emotion) {
        var archives = archiveRepository.findFirstPage(timeSortType, emotion, ARCHIVE_COMMUNITY_PAGE_ELEMENT_SIZE);
        return archives.stream()
                       .map(archive -> ArchiveCommunityResponseDto.from(archive, timeSortType.getMilli(archive)))
                       .toList();
    }

    public List<ArchiveCommunityResponseDto> archiveCommunityNextPage(ArchiveCommunityTimeSortType timeSortType,
                                                                      Emotion emotion,
                                                                      Long lastSeenArchiveDateMilli,
                                                                      Long lastSeenArchiveId) {
        var archives = archiveRepository.findNextPage(timeSortType,
                                                      emotion,
                                                      lastSeenArchiveDateMilli,
                                                      lastSeenArchiveId,
                                                      ARCHIVE_COMMUNITY_PAGE_ELEMENT_SIZE);
        return archives.stream()
                       .map(archive -> ArchiveCommunityResponseDto.from(archive, timeSortType.getMilli(archive)))
                       .toList();
    }

}

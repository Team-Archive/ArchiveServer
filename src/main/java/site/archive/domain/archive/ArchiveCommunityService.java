package site.archive.domain.archive;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.archive.api.v2.dto.ArchiveCommunityResponseDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArchiveCommunityService {

    private static final int ARCHIVE_COMMUNITY_PAGE_ELEMENT_SIZE = 15;

    private final ArchiveRepository archiveRepository;

    public List<ArchiveCommunityResponseDto> getCommunityFirstPage(ArchivePageable archivePageable) {
        var archives = archiveRepository.findFirstPage(archivePageable, ARCHIVE_COMMUNITY_PAGE_ELEMENT_SIZE);
        return archives.stream()
                       .map(archive -> ArchiveCommunityResponseDto.from(archive, archivePageable.getSortType().convertToMillis(archive)))
                       .toList();
    }

    public List<ArchiveCommunityResponseDto> getCommunityNextPage(ArchivePageable archivePageable) {
        var archives = archiveRepository.findNextPage(archivePageable, ARCHIVE_COMMUNITY_PAGE_ELEMENT_SIZE);
        return archives.stream()
                       .map(archive -> ArchiveCommunityResponseDto.from(archive, archivePageable.getSortType().convertToMillis(archive)))
                       .toList();
    }

}

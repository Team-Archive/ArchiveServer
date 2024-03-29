package site.archive.service.archive;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.archive.domain.archive.Archive;
import site.archive.domain.archive.ArchiveRepository;
import site.archive.domain.archive.custom.ArchivePageable;
import site.archive.dto.v2.ArchiveCommunityResponseDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArchiveCommunityService {

    private static final int ARCHIVE_COMMUNITY_PAGE_ELEMENT_SIZE = 15;

    private final ArchiveRepository archiveRepository;

    public List<ArchiveCommunityResponseDto> getCommunityFirstPage(Long currentUserIdx, ArchivePageable pageable) {
        var archiveIds = archiveRepository.findFirstPageOnlyPublic(pageable, ARCHIVE_COMMUNITY_PAGE_ELEMENT_SIZE).stream()
                                          .map(Archive::getId).toList();
        return archiveRepository.findByIdInWithLike(archiveIds, pageable).stream()
                                .map(archive -> ArchiveCommunityResponseDto.from(archive, currentUserIdx,
                                                                                 pageable.getSortType().convertToMillis(archive)))
                                .toList();
    }

    public List<ArchiveCommunityResponseDto> getCommunityNextPage(Long currentUserIdx, ArchivePageable pageable) {
        var archiveIds = archiveRepository.findNextPageOnlyPublic(pageable, ARCHIVE_COMMUNITY_PAGE_ELEMENT_SIZE).stream()
                                          .map(Archive::getId).toList();
        return archiveRepository.findByIdInWithLike(archiveIds, pageable).stream()
                                .map(archive -> ArchiveCommunityResponseDto.from(archive, currentUserIdx,
                                                                                 pageable.getSortType().convertToMillis(archive)))
                                .toList();
    }

}

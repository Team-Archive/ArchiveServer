package site.archive.service.archive;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.archive.common.exception.common.ResourceNotFoundException;
import site.archive.common.exception.common.UnauthorizedResourceException;
import site.archive.domain.archive.Archive;
import site.archive.domain.archive.ArchiveRepository;
import site.archive.domain.archive.custom.ArchivePageable;
import site.archive.domain.like.Like;
import site.archive.domain.user.UserInfo;
import site.archive.domain.user.UserRepository;
import site.archive.dto.v1.archive.ArchiveDtoV1;
import site.archive.dto.v1.archive.ArchiveListResponseDtoV1;
import site.archive.dto.v2.ArchiveDtoV2;
import site.archive.dto.v2.ArchiveLikeResponseDto;
import site.archive.dto.v2.MyArchiveResponseDto;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArchiveService {

    private static final int ARCHIVE_PAGE_ELEMENT_SIZE = 10;

    private final ArchiveRepository archiveRepository;
    private final UserRepository userRepository;

    /**
     * v1 controller에서 사용
     * 유저의 모든 Archive 조회
     * public/private 권한을 계산하지 않음
     *
     * @param userInfo 현재 유저의 정보
     * @return archive list
     */
    public ArchiveListResponseDtoV1 getAllArchive(UserInfo userInfo) {
        var authorId = userInfo.getUserId();
        var archiveDtos = archiveRepository.findAllByAuthorId(authorId).stream()
                                           .map(ArchiveDtoV1::simpleForm)
                                           .toList();
        return ArchiveListResponseDtoV1.from(archiveDtos);
    }

    /**
     * v2 controller에서 사용
     * 자신의 Archive의 경우, 모든 archive 조회
     * 타인의 Archive의 경우, public archive 만 조회
     *
     * @param userInfo 현재 유저의 정보
     * @param authorId 조회하고자 하는 아카이브의 작성자 아이디
     * @return archive list
     */
    public ArchiveListResponseDtoV1 getAllArchive(UserInfo userInfo, Long authorId) {
        var archiveDtos = archiveRepository.findAllByAuthorId(authorId).stream()
                                           .filter(hasViewAuthority(userInfo.getUserId()))
                                           .map(ArchiveDtoV1::simpleForm)
                                           .toList();
        return ArchiveListResponseDtoV1.from(archiveDtos);
    }

    public List<ArchiveLikeResponseDto> getAllArchive(Long currentUserId, List<Long> archiveIds) {
        return archiveRepository.findAllByIdIn(archiveIds).stream()
                                .filter(Archive::getIsPublic)
                                .sorted(Comparator.comparing(archive -> getLikeOfArchiveByUserId(currentUserId, archive).getUpdatedAt(),
                                                             Comparator.reverseOrder()))
                                .map(archive -> ArchiveLikeResponseDto.from(archive, currentUserId))
                                .toList();
    }


    public List<MyArchiveResponseDto> getAllArchiveFirstPage(UserInfo userInfo, ArchivePageable pageable) {
        var authorId = userInfo.getUserId();
        var archiveIds = archiveRepository.findFirstPageByAuthorId(authorId, pageable, ARCHIVE_PAGE_ELEMENT_SIZE).stream()
                                          .map(Archive::getId).toList();
        return archiveRepository.findByIdInWithLike(archiveIds, pageable).stream()
                                .map(archive -> MyArchiveResponseDto.from(archive,
                                                                          pageable.getSortType().convertToMillis(archive)))
                                .toList();
    }

    public List<MyArchiveResponseDto> getAllArchiveNextPage(UserInfo userInfo, ArchivePageable pageable) {
        var authorId = userInfo.getUserId();
        var archiveIds = archiveRepository.findNextPageByAuthorId(authorId, pageable, ARCHIVE_PAGE_ELEMENT_SIZE).stream()
                                          .map(Archive::getId).toList();
        return archiveRepository.findByIdInWithLike(archiveIds, pageable).stream()
                                .map(archive -> MyArchiveResponseDto.from(archive,
                                                                          pageable.getSortType().convertToMillis(archive)))
                                .toList();
    }

    /**
     * v1 controller에서 사용
     * archive 상세 조회
     * 유저의 정보 및 public/private 여부 계산하지 않음
     *
     * @param archiveId 상세 조회하려는 Archive id
     * @return Archive
     */
    public ArchiveDtoV1 getOneArchiveById(Long archiveId) {
        var archive = archiveRepository.findById(archiveId)
                                       .orElseThrow(() -> new ResourceNotFoundException("조회하려는 아카이브가 존재하지 않습니다"));
        return ArchiveDtoV1.specificForm(archive);
    }

    /**
     * v2 controller에서 사용
     * archive 상세 조회
     * 자신의 Archive의 경우, 모든 archive 조회
     * 타인의 Archive의 경우, public archive 만 조회
     *
     * @param userInfo  현재 유저의 정보
     * @param archiveId 상세 조회하려는 Archive id
     * @return Archive
     */
    public ArchiveDtoV2 getOneArchiveById(UserInfo userInfo, Long archiveId) {
        var archive = getOneArchiveOnlyHasAuthority(userInfo, archiveId);
        return ArchiveDtoV2.specificFrom(archive);
    }

    @Transactional
    public void delete(Long id) {
        var archive = archiveRepository.findById(id)
                                       .orElseThrow(() -> new ResourceNotFoundException("삭제하려는 아카이브가 존재하지 않습니다"));
        archiveRepository.delete(archive);
    }

    @Transactional
    public void save(ArchiveDtoV1 archiveDtoV1, Long authorId) {
        var user = userRepository.findById(authorId)
                                 .orElseThrow(() -> new ResourceNotFoundException("아이디에 해당하는 유저가 존재하지 않습니다."));
        var archive = archiveRepository.save(archiveDtoV1.toEntity(user));
        Objects.requireNonNull(archiveDtoV1.getImages()).stream()
               .map(archiveImageDto -> archiveImageDto.toEntity(archive))
               .forEach(archive::addImage);
    }

    public long countArchive(UserInfo userInfo) {
        var authorId = userInfo.getUserId();
        return archiveRepository.countArchiveByAuthorId(authorId);
    }

    public long countArchiveOfCurrentMonth(UserInfo userInfo) {
        return archiveRepository.countArchiveOfCurrentMonthByAuthorId(userInfo.getUserId());
    }

    public Optional<Long> getArchiveAuthorId(Long archiveId) {
        return archiveRepository.findById(archiveId)
                                .map(archive -> archive.getAuthor().getId());
    }

    @Transactional
    public void updateArchivePublicPrivate(UserInfo userInfo, Long archiveId, Boolean isPublic) {
        var archive = getOneArchiveOnlyHasAuthority(userInfo, archiveId);
        archive.updateToPublic(isPublic);
    }

    private Archive getOneArchiveOnlyHasAuthority(UserInfo userInfo, Long archiveId) {
        return archiveRepository.findById(archiveId)
                                .filter(hasViewAuthority(userInfo.getUserId()))
                                .orElseThrow(() -> new UnauthorizedResourceException("존재하지 않거나 권한이 없는 아카이브"));
    }

    private Predicate<Archive> hasViewAuthority(Long currentUserId) {
        return archive -> archive.getAuthor().getId().equals(currentUserId)
                          || archive.getIsPublic();
    }

    private Like getLikeOfArchiveByUserId(Long currentUserId, Archive archive) {
        return archive.getLikeByUserId(currentUserId)
                      .orElseThrow(() -> new ResourceNotFoundException("UserId에 속하는 Archive Like 리소스가 없습니다."));
    }

}
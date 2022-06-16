package site.archive.domain.archive;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.archive.api.dto.archive.ArchiveDto;
import site.archive.api.dto.archive.ArchiveListResponseDto;
import site.archive.domain.archive.entity.Archive;
import site.archive.domain.user.UserRepository;
import site.archive.domain.user.info.UserInfo;
import site.archive.exception.common.ResourceNotFoundException;
import site.archive.exception.common.UnauthorizedResourceException;

import java.util.function.Predicate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArchiveService {

    private final ArchiveRepository archiveRepository;
    private final UserRepository userRepository;

    /**
     * v1 controller에서 사용
     * 유저의 모든 Archive 조회
     * public/private 권한을 계산하지 않음
     *
     * @param info 현재 유저의 정보
     * @return archive list
     */
    public ArchiveListResponseDto getAllArchive(UserInfo info) {
        var authorId = info.getUserId();
        var archiveDtos = archiveRepository.findAllByAuthorUserId(authorId).stream()
                                           .map(ArchiveDto::simpleFrom)
                                           .toList();
        return ArchiveListResponseDto.from(archiveDtos);
    }

    /**
     * v2 controller에서 사용
     * 자신의 Archive의 경우, 모든 archive 조회
     * 타인의 Archive의 경우, public archive 만 조회
     *
     * @param info     현재 유저의 정보
     * @param authorId 조회하고자 하는 아카이브의 작성자 아이디
     * @return archive list
     */
    public ArchiveListResponseDto getAllArchive(UserInfo info, Long authorId) {
        var archiveDtos = archiveRepository.findAllByAuthorUserId(authorId).stream()
                                           .filter(hasViewAuthority(info.getUserId()))
                                           .map(ArchiveDto::simpleFrom)
                                           .toList();
        return ArchiveListResponseDto.from(archiveDtos);
    }

    /**
     * v1 controller에서 사용
     * archive 상세 조회
     * 유저의 정보 및 public/private 여부 계산하지 않음
     *
     * @param archiveId 상세 조회하려는 Archive id
     * @return Archive
     */
    public ArchiveDto getOneArchiveById(Long archiveId) {
        var archive = archiveRepository.findById(archiveId)
                                       .orElseThrow(() -> new ResourceNotFoundException("조회하려는 아카이브가 존재하지 않습니다"));
        return ArchiveDto.specificFrom(archive);
    }

    /**
     * v2 controller에서 사용
     * archive 상세 조회
     * 자신의 Archive의 경우, 모든 archive 조회
     * 타인의 Archive의 경우, public archive 만 조회
     *
     * @param info      현재 유저의 정보
     * @param archiveId 상세 조회하려는 Archive id
     * @return Archive
     */
    public ArchiveDto getOneArchiveById(UserInfo info, Long archiveId) {
        var archive = archiveRepository.findById(archiveId)
                                       .filter(hasViewAuthority(info.getUserId()))
                                       .orElseThrow(() -> new UnauthorizedResourceException("존재하지 않거나 권한이 없는 아카이브"));
        return ArchiveDto.specificFrom(archive);
    }

    @Transactional
    public void delete(Long id) {
        var archive = archiveRepository.findById(id)
                                       .orElseThrow(() -> new ResourceNotFoundException("삭제하려는 아카이브가 존재하지 않습니다"));
        archiveRepository.delete(archive);
    }

    @Transactional
    public void save(ArchiveDto archiveDto, Long authorId) {
        var user = userRepository.findById(authorId)
                                 .orElseThrow(() -> new ResourceNotFoundException("아이디에 해당하는 유저가 존재하지 않습니다."));
        var archive = archiveRepository.save(archiveDto.toEntity(user));
        archiveDto.getImages().stream()
                  .map(archiveImageDto -> archiveImageDto.toEntity(archive))
                  .forEach(archive::addImage);
    }

    public long countArchive(UserInfo info) {
        var authorId = info.getUserId();
        return archiveRepository.countArchiveByAuthorUserId(authorId);
    }

    private Predicate<Archive> hasViewAuthority(Long currentUserId) {
        return archive -> archive.getAuthor().getUserId().equals(currentUserId)
                          || archive.getIsPublic();
    }

}
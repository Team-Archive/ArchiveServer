package site.archive.api.v2.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import site.archive.domain.archive.entity.Archive;
import site.archive.domain.archive.entity.Emotion;

import static site.archive.common.DateTimeUtil.YY_MM_DD_FORMATTER;

@RequiredArgsConstructor
@Builder
@Getter
public class ArchiveCommunityResponseDto {

    private final Long archiveId;
    private final String name;
    private final String watchedOn;
    private final Emotion emotion;
    private final String mainImage;
    private final Long authorId;
    private final String authorNickname;
    private final String authorProfileImage;
    private final Boolean isLiked;
    private final Long likeCount;
    private final Long dateMilli;

    public static ArchiveCommunityResponseDto from(Archive archive, Long currentUserIdx, long dateMilli) {
        var author = archive.getAuthor();
        var isLiked = archive.getLikes().stream()
                             .anyMatch(like -> !like.getIsDeleted() && like.getUser().getId().equals(currentUserIdx));
        var likeCount = archive.getLikes().stream()
                               .filter(like -> !like.getIsDeleted()).count();
        return ArchiveCommunityResponseDto.builder()
                                          .archiveId(archive.getId())
                                          .name(archive.getName())
                                          .watchedOn(archive.getWatchedOn().format(YY_MM_DD_FORMATTER))
                                          .emotion(archive.getEmotion())
                                          .mainImage(archive.getMainImage())
                                          .authorId(author.getId())
                                          .authorNickname("")        // TODO: 추가필요
                                          .authorProfileImage("")    // TODO: 추가필요
                                          .isLiked(isLiked)
                                          .likeCount(likeCount)
                                          .dateMilli(dateMilli)
                                          .build();
    }

}



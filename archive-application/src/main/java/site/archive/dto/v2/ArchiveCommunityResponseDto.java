package site.archive.dto.v2;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import site.archive.common.DateTimeUtils;
import site.archive.domain.archive.Archive;
import site.archive.domain.archive.CoverImageType;
import site.archive.domain.archive.Emotion;

@RequiredArgsConstructor
@Builder
@Getter
public class ArchiveCommunityResponseDto {

    private final Long archiveId;
    private final String name;
    private final String watchedOn;
    private final Emotion emotion;
    private final String mainImage;
    private final CoverImageType coverImageType;
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
                                          .watchedOn(archive.getWatchedOn().format(DateTimeUtils.getYymmddFormatter()))
                                          .emotion(archive.getEmotion())
                                          .mainImage(archive.getMainImage())
                                          .coverImageType(archive.getCoverImageType())
                                          .authorId(author.getId())
                                          .authorNickname(author.getNickname())
                                          .authorProfileImage(author.getProfileImage())
                                          .isLiked(isLiked)
                                          .likeCount(likeCount)
                                          .dateMilli(dateMilli)
                                          .build();
    }

}



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
public class MyArchiveResponseDto {

    private final Long archiveId;
    private final String name;
    private final String watchedOn;
    private final Emotion emotion;
    private final String mainImage;
    private final Boolean isPublic;
    private final CoverImageType coverImageType;
    private final Long authorId;
    private final Long likeCount;
    private final Long dateMilli;

    public static MyArchiveResponseDto from(Archive archive, long dateMilli) {
        var author = archive.getAuthor();
        var likeCount = archive.getLikes().stream()
                               .filter(like -> !like.getIsDeleted()).count();
        return MyArchiveResponseDto.builder()
                                   .archiveId(archive.getId())
                                   .name(archive.getName())
                                   .watchedOn(archive.getWatchedOn().format(DateTimeUtils.getYymmddFormatter()))
                                   .emotion(archive.getEmotion())
                                   .mainImage(archive.getMainImage())
                                   .isPublic(archive.getIsPublic())
                                   .coverImageType(archive.getCoverImageType())
                                   .authorId(author.getId())
                                   .likeCount(likeCount)
                                   .dateMilli(dateMilli)
                                   .build();
    }

}



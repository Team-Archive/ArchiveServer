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
public class MyArchiveResponseDto {

    private final Long archiveId;
    private final String name;
    private final String watchedOn;
    private final Emotion emotion;
    private final String mainImage;
    private final Boolean isPublic;
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
                                   .watchedOn(archive.getWatchedOn().format(YY_MM_DD_FORMATTER))
                                   .emotion(archive.getEmotion())
                                   .mainImage(archive.getMainImage())
                                   .isPublic(archive.getIsPublic())
                                   .authorId(author.getId())
                                   .likeCount(likeCount)
                                   .dateMilli(dateMilli)
                                   .build();
    }

}



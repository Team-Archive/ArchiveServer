package site.archive.api.v2.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import site.archive.domain.archive.entity.Archive;
import site.archive.domain.archive.entity.Emotion;

import static site.archive.util.DateTimeUtil.YY_MM_DD_FORMATTER;

@RequiredArgsConstructor
@Builder
@Getter
public class MyArchiveResponseDto {

    private final Long archiveId;
    private final String name;
    private final String watchedOn;
    private final Emotion emotion;
    private final String mainImage;
    private final Long authorId;
    private final Long likeCount;
    private final Long dateMilli;

    public static MyArchiveResponseDto from(Archive archive, long dateMilli) {
        var author = archive.getAuthor();
        return MyArchiveResponseDto.builder()
                                   .archiveId(archive.getId())
                                   .name(archive.getName())
                                   .watchedOn(archive.getWatchedOn().format(YY_MM_DD_FORMATTER))
                                   .emotion(archive.getEmotion())
                                   .mainImage(archive.getMainImage())
                                   .authorId(author.getId())
                                   .likeCount(0L)             // TODO: 추가필요
                                   .dateMilli(dateMilli)
                                   .build();
    }

}


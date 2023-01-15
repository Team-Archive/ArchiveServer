package site.archive.dto.v1.archive;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.archive.domain.archive.Archive;
import site.archive.domain.archive.CoverImageType;
import site.archive.domain.archive.Emotion;
import site.archive.domain.user.BaseUser;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static site.archive.common.DateTimeUtil.YY_MM_DD_FORMATTER;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@JsonInclude(Include.NON_NULL)
public class ArchiveDtoV1 {
    private Long archiveId;
    private String name;
    private String watchedOn;
    private Emotion emotion;
    private String mainImage;
    private Boolean isPublic;               // Default value is false
    private CoverImageType coverImageType;  // Default value is EMOTION_COVER

    private long authorId;

    private List<String> companions;
    private List<ArchiveImageDtoV1> images;

    /**
     * 아카이브 상세 조회 DTO V1
     * 아카이브 연결 이미지들을 다 포함
     *
     * @param archive Archive Entity
     * @return ArchiveDtoV1 archive specific DTO
     */
    public static ArchiveDtoV1 specificFrom(Archive archive) {
        var archiveImages = archive.getArchiveImages().stream()
                                   .map(ArchiveImageDtoV1::from)
                                   .toList();
        return ArchiveDtoV1.builder()
                           .archiveId(archive.getId())
                           .name(archive.getName())
                           .watchedOn(archive.getWatchedOn().format(YY_MM_DD_FORMATTER))
                           .emotion(archive.getEmotion())
                           .mainImage(archive.getMainImage())
                           .companions(archive.getCompanions())
                           .images(archiveImages)
                           .authorId(archive.getAuthor().getId())
                           .isPublic(archive.getIsPublic())
                           .build();
    }

    /**
     * 아카이브 리스트 조회 DTO
     * 아카이브 연결 이미지들을 포함하고 있지 않음
     *
     * @param archive archive entity
     * @return ArchiveDtoV1 archive simple DTO
     */
    public static ArchiveDtoV1 simpleFrom(Archive archive) {
        return ArchiveDtoV1.builder()
                           .archiveId(archive.getId())
                           .name(archive.getName())
                           .watchedOn(archive.getWatchedOn().format(YY_MM_DD_FORMATTER))
                           .emotion(archive.getEmotion())
                           .companions(archive.getCompanions())
                           .mainImage(archive.getMainImage())
                           .authorId(archive.getAuthor().getId())
                           .isPublic(archive.getIsPublic())
                           .coverImageType(archive.getCoverImageType())
                           .build();
    }

    public Archive toEntity(BaseUser user) {
        var archivePublic = this.isPublic != null && this.isPublic;
        var archiveCoverImageType = Objects.requireNonNullElse(coverImageType, CoverImageType.EMOTION_COVER);
        return Archive.builder()
                      .name(name)
                      .watchedOn(LocalDate.parse(watchedOn, YY_MM_DD_FORMATTER))
                      .emotion(emotion)
                      .mainImage(mainImage)
                      .companions(companions)
                      .author(user)
                      .isPublic(archivePublic)
                      .coverImageType(archiveCoverImageType)
                      .build();
    }

}

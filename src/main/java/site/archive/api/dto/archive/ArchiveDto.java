package site.archive.api.dto.archive;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.archive.domain.archive.entity.Archive;
import site.archive.domain.archive.entity.Emotion;
import site.archive.domain.user.entity.BaseUser;

import java.time.LocalDate;
import java.util.List;

import static site.archive.util.DateTimeUtil.YY_MM_DD_FORMATTER;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@JsonInclude(Include.NON_NULL)
public class ArchiveDto {
    private Long archiveId;
    private String name;
    private String watchedOn;
    private Emotion emotion;
    private String mainImage;
    private Boolean isPublic;       // Default value is false

    private long authorId;

    private List<String> companions;
    private List<ArchiveImageDto> images;

    public static ArchiveDto specificFrom(Archive archive) {
        var archiveImages = archive.getArchiveImages().stream()
                                   .map(ArchiveImageDto::from)
                                   .toList();
        return ArchiveDto.builder()
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

    public static ArchiveDto simpleFrom(Archive archive) {
        return ArchiveDto.builder()
                         .archiveId(archive.getId())
                         .name(archive.getName())
                         .watchedOn(archive.getWatchedOn().format(YY_MM_DD_FORMATTER))
                         .emotion(archive.getEmotion())
                         .companions(archive.getCompanions())
                         .mainImage(archive.getMainImage())
                         .authorId(archive.getAuthor().getId())
                         .isPublic(archive.getIsPublic())
                         .build();
    }

    public Archive toEntity(BaseUser user) {
        var defaultIsPublic = this.isPublic != null && this.isPublic;
        return Archive.builder()
                      .name(name)
                      .watchedOn(LocalDate.parse(watchedOn, YY_MM_DD_FORMATTER))
                      .emotion(emotion)
                      .mainImage(mainImage)
                      .companions(companions)
                      .author(user)
                      .isPublic(defaultIsPublic)
                      .build();
    }

}

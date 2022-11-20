package site.archive.dto.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.archive.domain.archive.Archive;
import site.archive.domain.archive.Emotion;
import site.archive.domain.user.BaseUser;
import site.archive.dto.v1.archive.ArchiveImageDtoV1;

import java.time.LocalDate;
import java.util.List;

import static site.archive.common.DateTimeUtil.YY_MM_DD_FORMATTER;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArchiveDtoV2 {

    private Long archiveId;
    private String name;
    private String watchedOn;
    private Emotion emotion;
    private String mainImage;
    private Boolean isPublic;       // Default value is false

    private long authorId;
    private String nickname;
    private String profileImage;

    private List<String> companions;
    private List<ArchiveImageDtoV1> images;

    public static ArchiveDtoV2 specificFrom(Archive archive) {
        var archiveImages = archive.getArchiveImages().stream()
                                   .map(ArchiveImageDtoV1::from)
                                   .toList();
        var author = archive.getAuthor();
        return ArchiveDtoV2.builder()
                           .archiveId(archive.getId())
                           .name(archive.getName())
                           .watchedOn(archive.getWatchedOn().format(YY_MM_DD_FORMATTER))
                           .emotion(archive.getEmotion())
                           .mainImage(archive.getMainImage())
                           .companions(archive.getCompanions())
                           .images(archiveImages)
                           .authorId(author.getId())
                           .nickname(author.getNickname())
                           .profileImage(author.getProfileImage())
                           .isPublic(archive.getIsPublic())
                           .build();
    }
}

package site.archive.dto.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.archive.common.DateTimeUtils;
import site.archive.domain.archive.Archive;
import site.archive.domain.archive.CoverImageType;
import site.archive.domain.archive.Emotion;
import site.archive.dto.v1.archive.ArchiveImageDtoV1;

import java.util.List;

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
    private Boolean isPublic;               // Default value is false
    private CoverImageType coverImageType;  // Default value is EMOTION_COVER

    private long authorId;
    private String nickname;
    private String profileImage;

    private List<String> companions;
    private List<ArchiveImageDtoV1> images;

    /**
     * 아카이브 상세 조회 DTO V2
     * 아카이브 작가의 닉네임, 프로필이미지 포함
     * 아카이브 연결 이미지들을 다 포함
     *
     * @param archive Archive Entity
     * @return ArchiveDtoV2 archive specific DTO
     */
    public static ArchiveDtoV2 specificFrom(Archive archive) {
        var archiveImages = archive.getArchiveImages().stream()
                                   .map(ArchiveImageDtoV1::from)
                                   .toList();
        var author = archive.getAuthor();
        return ArchiveDtoV2.builder()
                           .archiveId(archive.getId())
                           .name(archive.getName())
                           .watchedOn(archive.getWatchedOn().format(DateTimeUtils.getYymmddFormatter()))
                           .emotion(archive.getEmotion())
                           .mainImage(archive.getMainImage())
                           .companions(archive.getCompanions())
                           .images(archiveImages)
                           .authorId(author.getId())
                           .nickname(author.getNickname())
                           .profileImage(author.getProfileImage())
                           .isPublic(archive.getIsPublic())
                           .coverImageType(archive.getCoverImageType())
                           .build();
    }
}

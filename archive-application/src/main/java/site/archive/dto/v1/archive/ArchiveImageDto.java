package site.archive.dto.v1.archive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.archive.domain.archive.Archive;
import site.archive.domain.archive.ArchiveImage;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ArchiveImageDto {

    private Long archiveImageId;
    private String image;
    private String review;
    private String backgroundColor;

    public static ArchiveImageDto from(ArchiveImage archiveImage) {
        return ArchiveImageDto.builder()
                              .archiveImageId(archiveImage.getId())
                              .image(archiveImage.getImage())
                              .review(archiveImage.getReview())
                              .backgroundColor(archiveImage.getBackgroundColor())
                              .build();
    }

    public ArchiveImage toEntity(Archive archive) {
        return new ArchiveImage(image, review, backgroundColor, archive);
    }

}
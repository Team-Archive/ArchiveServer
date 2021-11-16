package com.depromeet.archive.api.dto.archive;

import com.depromeet.archive.domain.archive.entity.ArchiveImage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArchiveImageDto {

    private final Long archiveImageId;
    private final String image;
    private final String review;

    public static ArchiveImageDto from(ArchiveImage archiveImage) {
        return ArchiveImageDto.builder()
                .archiveImageId(archiveImage.getId())
                .image(archiveImage.getImage())
                .review(archiveImage.getReview())
                .build();
    }

}
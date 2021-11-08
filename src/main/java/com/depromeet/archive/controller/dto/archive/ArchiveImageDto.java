package com.depromeet.archive.controller.dto.archive;

import com.depromeet.archive.domain.archive.entity.ArchiveImage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArchiveImageDto {

    private Long id;
    private String image;
    private String review;

    public static ArchiveImageDto from(ArchiveImage archiveImage) {
        return ArchiveImageDto.builder()
                .id(archiveImage.getId())
                .image(archiveImage.getImage())
                .review(archiveImage.getReview())
                .build();
    }

}
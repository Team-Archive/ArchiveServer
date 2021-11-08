package com.depromeet.archive.controller.dto.archive;

import com.depromeet.archive.domain.archive.entity.Archive;
import com.depromeet.archive.domain.archive.entity.Emotion;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ArchiveDto {

    private Long id;
    private String name;
    private String watchedOn;
    private Emotion emotion;
    private String mainImage;
    private List<String> companions;
    private List<ArchiveImageDto> archiveImages;

    public static ArchiveDto from(Archive archive) {
        var archiveImages = archive.getArchiveImages().stream()
                .map(ArchiveImageDto::from)
                .collect(Collectors.toList());

        return ArchiveDto.builder()
                .id(archive.getId())
                .name(archive.getName())
                .watchedOn(archive.getWatchedOn())
                .emotion(archive.getEmotion())
                .mainImage(archive.getMainImage())
                .companions(archive.getCompanions())
                .archiveImages(archiveImages)
                .build();
    }
}

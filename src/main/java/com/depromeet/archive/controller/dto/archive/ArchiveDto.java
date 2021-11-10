package com.depromeet.archive.controller.dto.archive;

import com.depromeet.archive.domain.archive.entity.Archive;
import com.depromeet.archive.domain.archive.entity.Emotion;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@JsonInclude(Include.NON_NULL)
public class ArchiveDto {

    private final Long archiveId;
    private final String name;
    private final String watchedOn;
    private final Emotion emotion;
    private final String mainImage;
    private final List<String> companions;
    private final List<ArchiveImageDto> images;

    public static ArchiveDto specificFrom(Archive archive) {
        var archiveImages = archive.getArchiveImages().stream()
                .map(ArchiveImageDto::from)
                .collect(Collectors.toList());
        return ArchiveDto.builder()
                .archiveId(archive.getId())
                .name(archive.getName())
                .watchedOn(archive.getWatchedOn())
                .emotion(archive.getEmotion())
                .mainImage(archive.getMainImage())
                .companions(archive.getCompanions())
                .images(archiveImages)
                .build();
    }

    public static ArchiveDto simpleFrom(Archive archive) {
        return ArchiveDto.builder()
                .archiveId(archive.getId())
                .name(archive.getName())
                .watchedOn(archive.getWatchedOn())
                .emotion(archive.getEmotion())
                .companions(archive.getCompanions())
                .mainImage(archive.getMainImage())
                .build();
    }

}

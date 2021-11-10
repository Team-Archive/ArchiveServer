package com.depromeet.archive.controller.dto.archive;

import lombok.Getter;

import java.util.List;

@Getter
public class ArchiveListDto {

    private final int archiveCount;
    private final List<ArchiveDto> archives;

    private ArchiveListDto(List<ArchiveDto> archives) {
        this.archiveCount = archives.size();
        this.archives = archives;
    }

    public static ArchiveListDto from(List<ArchiveDto> archiveDtos) {
        return new ArchiveListDto(archiveDtos);
    }

}
package site.archive.api.v1.dto.archive;

import lombok.Getter;

import java.util.List;

@Getter
public class ArchiveListResponseDto {

    private final int archiveCount;
    private final List<ArchiveDto> archives;

    private ArchiveListResponseDto(List<ArchiveDto> archives) {
        this.archiveCount = archives.size();
        this.archives = archives;
    }

    public static ArchiveListResponseDto from(List<ArchiveDto> archiveDtos) {
        return new ArchiveListResponseDto(archiveDtos);
    }

}
package site.archive.dto.v2;

import lombok.Getter;

import java.util.List;

@Getter
public class MyArchiveListResponseDto {

    private final Long archiveCount;
    private final List<MyArchiveResponseDto> archives;

    private MyArchiveListResponseDto(Long archiveCount, List<MyArchiveResponseDto> archives) {
        this.archiveCount = archiveCount;
        this.archives = archives;
    }

    public static MyArchiveListResponseDto from(Long archiveCount, List<MyArchiveResponseDto> archiveDtos) {
        return new MyArchiveListResponseDto(archiveCount, archiveDtos);
    }

}
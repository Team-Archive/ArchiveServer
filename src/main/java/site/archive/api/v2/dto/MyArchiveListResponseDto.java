package site.archive.api.v2.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class MyArchiveListResponseDto {

    private final List<MyArchiveResponseDto> archives;

    private MyArchiveListResponseDto(List<MyArchiveResponseDto> archives) {
        this.archives = archives;
    }

    public static MyArchiveListResponseDto from(List<MyArchiveResponseDto> archiveDtos) {
        return new MyArchiveListResponseDto(archiveDtos);
    }

}
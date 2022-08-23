package site.archive.dto.v2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class ArchiveLikeListResponseDto {

    private final Integer archiveCount;
    private final List<ArchiveLikeResponseDto> archives;

}



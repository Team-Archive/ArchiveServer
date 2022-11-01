package site.archive.dto.v1.archive;

import lombok.Getter;

import java.util.List;

@Getter
public class ArchiveListResponseDtoV1 {

    private final int archiveCount;
    private final List<ArchiveDtoV1> archives;

    private ArchiveListResponseDtoV1(List<ArchiveDtoV1> archives) {
        this.archiveCount = archives.size();
        this.archives = archives;
    }

    public static ArchiveListResponseDtoV1 from(List<ArchiveDtoV1> archiveDtoV1s) {
        return new ArchiveListResponseDtoV1(archiveDtoV1s);
    }

}
package site.archive.dto.v1.archive

data class ArchiveListResponseDtoV1(
    val archiveCount: Int,
    val archives: List<ArchiveDtoV1>
) {

    private constructor(archives: List<ArchiveDtoV1>) : this(archives.size, archives)

    companion object {
        @JvmStatic
        fun from(archiveDtoV1s: List<ArchiveDtoV1>): ArchiveListResponseDtoV1 {
            return ArchiveListResponseDtoV1(archiveDtoV1s)
        }
    }

}
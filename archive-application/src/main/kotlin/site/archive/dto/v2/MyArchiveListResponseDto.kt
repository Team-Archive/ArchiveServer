package site.archive.dto.v2

data class MyArchiveListResponseDto(
    val archiveCount: Long,
    val archives: List<MyArchiveResponseDto>
) {
    companion object {
        @JvmStatic
        fun from(archiveCount: Long, archiveDtos: List<MyArchiveResponseDto>): MyArchiveListResponseDto {
            return MyArchiveListResponseDto(archiveCount, archiveDtos)
        }
    }
}
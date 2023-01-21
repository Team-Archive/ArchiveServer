package site.archive.dto.v2

import site.archive.common.yymmddFormatter
import site.archive.domain.archive.Archive
import site.archive.domain.archive.CoverImageType
import site.archive.domain.archive.Emotion

class MyArchiveResponseDto(
    val archiveId: Long,
    val name: String,
    val watchedOn: String,
    val emotion: Emotion,
    val mainImage: String,
    val isPublic: Boolean,
    val coverImageType: CoverImageType,
    val authorId: Long,
    val likeCount: Long,
    val dateMilli: Long
) {
    companion object {
        @JvmStatic
        fun from(archive: Archive, dateMilli: Long): MyArchiveResponseDto {
            val author = archive.author
            val likeCount = archive.likes.stream()
                .filter { !it.isDeleted }
                .count()
            return MyArchiveResponseDto(
                archiveId = archive.id,
                name = archive.name,
                watchedOn = archive.watchedOn.format(yymmddFormatter),
                emotion = archive.emotion,
                mainImage = archive.mainImage,
                isPublic = archive.isPublic,
                coverImageType = archive.coverImageType,
                authorId = author.id,
                likeCount = likeCount,
                dateMilli = dateMilli
            )
        }
    }
}
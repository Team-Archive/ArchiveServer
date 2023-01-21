package site.archive.dto.v2

import com.fasterxml.jackson.annotation.JsonInclude
import site.archive.common.yymmddFormatter
import site.archive.domain.archive.Archive
import site.archive.domain.archive.CoverImageType
import site.archive.domain.archive.Emotion
import site.archive.dto.v1.archive.ArchiveImageDtoV1

@JsonInclude(JsonInclude.Include.NON_NULL)
class ArchiveDtoV2(
    val archiveId: Long,
    val name: String,
    val watchedOn: String,
    val emotion: Emotion,
    val mainImage: String,
    val isPublic: Boolean,
    val coverImageType: CoverImageType,
    val authorId: Long,
    val nickname: String,
    val profileImage: String,
    val companions: List<String>? = null,
    val images: List<ArchiveImageDtoV1>? = null
) {
    companion object {
        @JvmStatic
        fun specificFrom(archive: Archive): ArchiveDtoV2 {
            val archiveImages = archive.archiveImages.stream()
                .map(ArchiveImageDtoV1::from)
                .toList()
            val author = archive.author
            return ArchiveDtoV2(
                archiveId = archive.id,
                name = archive.name,
                watchedOn = archive.watchedOn.format(yymmddFormatter),
                emotion = archive.emotion,
                mainImage = archive.mainImage,
                companions = archive.companions,
                images = archiveImages,
                authorId = author.id,
                nickname = author.nickname,
                profileImage = author.profileImage,
                isPublic = archive.isPublic,
                coverImageType = archive.coverImageType
            )
        }

    }
}
package site.archive.dto.v1.archive

import site.archive.domain.archive.Archive
import site.archive.domain.archive.ArchiveImage

data class ArchiveImageDtoV1(
    val archiveImageId: Long?,
    val image: String,
    val review: String,
    val backgroundColor: String
) {

    fun toEntity(archive: Archive): ArchiveImage {
        return ArchiveImage(image, review, backgroundColor, archive)
    }

    companion object {
        @JvmStatic
        fun from(archiveImage: ArchiveImage): ArchiveImageDtoV1 {
            return ArchiveImageDtoV1(
                archiveImageId = archiveImage.id,
                image = archiveImage.image,
                review = archiveImage.review,
                backgroundColor = archiveImage.backgroundColor
            )
        }
    }

}
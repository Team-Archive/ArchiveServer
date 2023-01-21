package site.archive.dto.v2

import site.archive.common.yymmddFormatter
import site.archive.domain.archive.Archive
import site.archive.domain.archive.CoverImageType
import site.archive.domain.archive.Emotion

class ArchiveCommunityResponseDto(
    val archiveId: Long,
    val name: String,
    val watchedOn: String,
    val emotion: Emotion,
    val mainImage: String,
    val coverImageType: CoverImageType,
    val authorId: Long,
    val authorNickname: String,
    val authorProfileImage: String,
    val isLiked: Boolean,
    val likeCount: Long,
    val dateMilli: Long
) {
    companion object {
        @JvmStatic
        fun from(archive: Archive, currentUserIdx: Long, dateMilli: Long): ArchiveCommunityResponseDto {
            val author = archive.author
            val isLiked = archive.likes.stream()
                .anyMatch { !it.isDeleted && it.user.id.equals(currentUserIdx) }
            val likeCount = archive.likes.stream()
                .filter { !it.isDeleted }
                .count()
            return ArchiveCommunityResponseDto(
                archiveId = archive.id,
                name = archive.name,
                watchedOn = archive.watchedOn.format(yymmddFormatter),
                emotion = archive.emotion,
                mainImage = archive.mainImage,
                coverImageType = archive.coverImageType,
                authorId = author.id,
                authorNickname = author.nickname,
                authorProfileImage = author.profileImage,
                isLiked = isLiked,
                likeCount = likeCount,
                dateMilli = dateMilli
            )
        }
    }
}
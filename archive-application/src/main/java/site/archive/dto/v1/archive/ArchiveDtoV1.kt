package site.archive.dto.v1.archive

import com.fasterxml.jackson.annotation.JsonInclude
import site.archive.common.yymmddFormatter
import site.archive.domain.archive.Archive
import site.archive.domain.archive.CoverImageType
import site.archive.domain.archive.Emotion
import site.archive.domain.user.BaseUser
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ArchiveDtoV1(
    val archiveId: Long,
    val name: String,
    val watchedOn: String,
    val emotion: Emotion,
    val mainImage: String,
    val authorId: Long,
    val isPublic: Boolean? = null,
    val coverImageType: CoverImageType? = null,
    var companions: List<String>? = null,
    var images: List<ArchiveImageDtoV1>? = null
) {

    fun toEntity(user: BaseUser): Archive {
        val archivePublic = isPublic ?: false
        val archiveCoverImageType = coverImageType ?: CoverImageType.EMOTION_COVER
        return Archive.builder()
            .name(name)
            .watchedOn(LocalDate.parse(watchedOn, yymmddFormatter))
            .emotion(emotion)
            .mainImage(mainImage)
            .companions(companions)
            .author(user)
            .isPublic(archivePublic)
            .coverImageType(archiveCoverImageType)
            .build()
    }

    companion object {
        /**
         * 아카이브 상세 조회 DTO V1
         * 아카이브 연결 이미지들을 다 포함
         *
         * @param archive Archive Entity
         * @return ArchiveDtoV1 archive specific DTO
         */
        @JvmStatic
        fun specificForm(archive: Archive): ArchiveDtoV1 {
            val archiveImages = archive.archiveImages.map(ArchiveImageDtoV1::from).toList()
            return ArchiveDtoV1(
                archiveId = archive.id,
                name = archive.name,
                watchedOn = archive.watchedOn.format(yymmddFormatter),
                emotion = archive.emotion,
                mainImage = archive.mainImage,
                companions = archive.companions,
                images = archiveImages,
                authorId = archive.author.id,
                isPublic = archive.isPublic
            )
        }

        /**
         * 아카이브 리스트 조회 DTO
         * 아카이브 연결 이미지들을 포함하고 있지 않음
         *
         * @param archive archive entity
         * @return ArchiveDtoV1 archive simple DTO
         */
        @JvmStatic
        fun simpleForm(archive: Archive): ArchiveDtoV1 {
            return ArchiveDtoV1(
                archiveId = archive.id,
                name = archive.name,
                watchedOn = archive.watchedOn.format(yymmddFormatter),
                emotion = archive.emotion,
                companions = archive.companions,
                mainImage = archive.mainImage,
                authorId = archive.author.id,
                isPublic = archive.isPublic,
                coverImageType = archive.coverImageType
            )
        }
    }

}
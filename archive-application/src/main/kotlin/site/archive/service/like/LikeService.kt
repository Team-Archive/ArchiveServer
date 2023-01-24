package site.archive.service.like

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.archive.common.exception.common.ResourceNotFoundException
import site.archive.domain.common.BaseTimeEntity
import site.archive.domain.like.Like
import site.archive.domain.like.LikeRepository

@Service
@Transactional(readOnly = true)
class LikeService(private val likeRepository: LikeRepository) {

    @Transactional
    fun save(userId: Long, archiveId: Long) {
        likeRepository.findByUserIdAndArchiveId(userId, archiveId)
            .ifPresentOrElse(BaseTimeEntity::softDeleteCancel) { likeRepository.save(Like.of(userId, archiveId)) }
    }

    @Transactional
    fun save(userId: Long, archiveIds: List<Long>) {
        archiveIds.forEach {
            likeRepository.findByUserIdAndArchiveId(userId, it)
                .ifPresentOrElse(BaseTimeEntity::softDeleteCancel) { likeRepository.save(Like.of(userId, it)) }
        }
    }

    @Transactional
    fun delete(userId: Long, archiveId: Long) {
        likeRepository.findByUserIdAndArchiveId(userId, archiveId)
            .ifPresentOrElse(likeRepository::delete) { throw ResourceNotFoundException("조건에 맞는 Like 데이터가 없습니다") }
    }

    @Transactional
    fun delete(userId: Long, archiveIds: List<Long>) {
        archiveIds.forEach {
            likeRepository.findByUserIdAndArchiveId(userId, it)
                .ifPresentOrElse(likeRepository::delete) { throw ResourceNotFoundException("조건에 맞는 Like 데이터가 없습니다") }
        }
    }

    fun likeArchiveIds(userId: Long): List<Long> {
        return likeRepository.findAllByUserId(userId)
            .filter { !it.isDeleted }
            .map { it.archive.id }
            .toList()
    }

}
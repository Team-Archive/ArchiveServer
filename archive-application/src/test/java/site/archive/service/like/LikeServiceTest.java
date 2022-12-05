package site.archive.service.like;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.archive.common.exception.common.ResourceNotFoundException;
import site.archive.domain.like.Like;
import site.archive.domain.like.LikeRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

    @Mock
    LikeRepository likeRepository;

    LikeService likeService;

    @BeforeEach
    void setUp() {
        likeService = new LikeService(likeRepository);
    }

    @DisplayName("이미 제거된 좋아요가 있을 때, soft delete를 취소한다")
    @Test
    void cancelSoftDeleteWhenDeletedLikeEntityExists() {

        // given
        var userId = 1L;
        var archiveId = 1L;
        var deletedLikeEntity = Like.of(userId, archiveId);
        deletedLikeEntity.softDelete();

        given(likeRepository.findByUserIdAndArchiveId(userId, archiveId))
            .willReturn(Optional.of(deletedLikeEntity));

        // when
        likeService.save(userId, archiveId);

        // then
        assertThat(deletedLikeEntity.getIsDeleted()).isFalse();
        verify(likeRepository, never()).save(deletedLikeEntity);
    }


    @DisplayName("Like entity가 없다면 저장한다")
    @Test
    void saveLikeEntityWhenLikeEntityNotExists() {

        // given
        var userId = 1L;
        var archiveId = 1L;
        given(likeRepository.findByUserIdAndArchiveId(userId, archiveId))
            .willReturn(Optional.empty());

        // when
        likeService.save(userId, archiveId);

        // then
        verify(likeRepository).save(any(Like.class));
    }

    @DisplayName("제거과정에서 Like entity가 있으면 delete가 호출된다")
    @Test
    void deleteWhenLikeExists() {

        // given
        var userId = 1L;
        var archiveId = 1L;
        var likeEntity = Like.of(userId, archiveId);
        given(likeRepository.findByUserIdAndArchiveId(userId, archiveId))
            .willReturn(Optional.of(likeEntity));

        // when
        likeService.delete(userId, archiveId);

        // then
        verify(likeRepository).delete(likeEntity);
    }

    @DisplayName("제거과정에서 Like entity가 없으면 예외가 발생한다")
    @Test
    void deleteProcessThrowExceptionWhenLikeEntityNotExists() {

        // given
        var userId = 1L;
        var archiveId = 1L;
        given(likeRepository.findByUserIdAndArchiveId(userId, archiveId))
            .willReturn(Optional.empty());

        // when & then

        // then
        assertThatThrownBy(() -> likeService.delete(userId, archiveId))
            .isInstanceOf(ResourceNotFoundException.class);
    }

}
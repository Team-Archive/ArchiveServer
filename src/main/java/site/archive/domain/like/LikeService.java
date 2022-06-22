package site.archive.domain.like;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.archive.domain.like.entity.Like;
import site.archive.exception.common.ResourceNotFoundException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    @Transactional
    public void save(Long userId, Long archiveId) {
        likeRepository.save(Like.of(userId, archiveId));
    }

    @Transactional
    public void delete(Long userId, Long archiveId) {
        var like = likeRepository.findByUserIdAndArchiveId(userId, archiveId)
                                 .orElseThrow(() -> new ResourceNotFoundException("조건에 맞는 Like 데이터가 없습니다"));
        likeRepository.delete(like);
    }

}

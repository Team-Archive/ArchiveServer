package site.archive.domain.like;

import org.springframework.data.jpa.repository.JpaRepository;
import site.archive.domain.like.entity.Like;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserIdAndArchiveId(Long userId, Long archiveId);

    List<Like> findByArchiveId(Long archiveId);

}
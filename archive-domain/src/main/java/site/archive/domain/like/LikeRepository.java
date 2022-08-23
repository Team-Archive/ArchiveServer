package site.archive.domain.like;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserIdAndArchiveId(Long userId, Long archiveId);

    List<Like> findByArchiveId(Long archiveId);

    List<Like> findAllByUserId(Long userId);

}
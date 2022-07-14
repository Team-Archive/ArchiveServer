package site.archive.domain.banner;

import org.springframework.data.jpa.repository.JpaRepository;
import site.archive.domain.banner.entity.Banner;

import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, Long> {

    List<Banner> findAllByOrderByCreatedAtDesc();

}

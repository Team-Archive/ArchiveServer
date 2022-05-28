package site.archive.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import site.archive.domain.user.entity.BaseUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<BaseUser, Long> {
    Optional<BaseUser> findByMailAddress(String mailAddress);
}

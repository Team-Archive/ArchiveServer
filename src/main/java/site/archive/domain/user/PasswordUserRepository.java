package site.archive.domain.user;

import site.archive.domain.user.entity.PasswordUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordUserRepository extends JpaRepository<PasswordUser, Long> {
    Optional<PasswordUser> findPasswordUserByMailAddress(String mailAddress);
}

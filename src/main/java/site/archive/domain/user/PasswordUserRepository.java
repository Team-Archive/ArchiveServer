package site.archive.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import site.archive.domain.user.entity.PasswordUser;

import java.util.Optional;

public interface PasswordUserRepository extends JpaRepository<PasswordUser, Long> {
    Optional<PasswordUser> findPasswordUserByMailAddress(String mailAddress);
}

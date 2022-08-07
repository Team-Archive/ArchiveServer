package site.archive.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotNull;

import java.util.Optional;

public interface UserRepository extends JpaRepository<BaseUser, Long> {
    Optional<BaseUser> findByMailAddress(String mailAddress);

    @Modifying
    @Query("update BaseUser u set u.isDeleted = true where u.id = :userId")
    void deleteById(@NotNull @Param("userId") Long userId);

}

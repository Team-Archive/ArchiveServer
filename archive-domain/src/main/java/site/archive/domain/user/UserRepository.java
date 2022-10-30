package site.archive.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotNull;

import java.util.Optional;

public interface UserRepository extends JpaRepository<BaseUser, Long> {
    Optional<BaseUser> findByMailAddress(String mailAddress);

    Optional<BaseUser> findByNickname(String nickname);

    @Modifying
    @Query("update BaseUser u set u.isDeleted = true where u.id = :userId")
    void deleteById(@NotNull @Param("userId") Long userId);

    @Modifying
    @Query("update BaseUser u set u.profileImage = :profileImage where u.id = :userId")
    void updateUserProfileImage(@Param("userId") Long userId,
                                @Param("profileImage") String profileImageUri);

    @Modifying
    @Query("update BaseUser u set u.nickname = :nickname where u.id = :userId")
    void updateNickName(@Param("userId") Long userId,
                        @Param("nickname") String nickname);

}

package com.depromeet.archive.infra.user.jpa;

import com.depromeet.archive.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
    User findUserByMailAddress(String mailAddress);
}

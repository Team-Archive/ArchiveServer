package com.depromeet.archive.infra.user.jpa;

import com.depromeet.archive.domain.user.entity.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<BaseUser, Long> {
    Optional<BaseUser> findUserByMailAddress(String mailAddress);
}

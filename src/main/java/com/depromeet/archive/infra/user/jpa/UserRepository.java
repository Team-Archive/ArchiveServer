package com.depromeet.archive.infra.user.jpa;

import com.depromeet.archive.domain.user.entity.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<BaseUser, Long> {
    Optional<BaseUser> findByMailAddress(String mailAddress);
}

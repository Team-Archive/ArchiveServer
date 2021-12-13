package com.depromeet.archive.infra.user.jpa;

import com.depromeet.archive.domain.user.entity.PasswordUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PasswordUserRepository extends JpaRepository<PasswordUser, Long> {
    PasswordUser findPasswordUserByMailAddress(String mailAddress);
}

package com.depromeet.archive.infra.user;

import com.depromeet.archive.exception.common.DuplicateResourceException;
import com.depromeet.archive.exception.common.ResourceNotFoundException;
import com.depromeet.archive.domain.user.UserReader;
import com.depromeet.archive.domain.user.UserStore;
import com.depromeet.archive.domain.user.entity.User;
import com.depromeet.archive.infra.user.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

@RequiredArgsConstructor
public class UserRepository implements UserReader, UserStore {

    private final UserJpaRepository jpaRepository;

    @Override
    public User findUserByMail(String mailAddress) {
        User user = jpaRepository.findUserByMailAddress(mailAddress);
        if (user == null)
            throw new ResourceNotFoundException("주어진 이메일에 맞는 유저가 없습니다", mailAddress);
        return user;
    }

    @Override
    public User findUserById(long userId) {
        return jpaRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("인덱스에 해당하는 유저가 없습니다", String.valueOf(userId)));
    }

    @Override
    public void saveUser(User user) {
        try {
            jpaRepository.saveAndFlush(user);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateResourceException(user.getMailAddress());
        }
    }

    @Override
    public void removeUser(User user) {
        jpaRepository.delete(user);
    }
}

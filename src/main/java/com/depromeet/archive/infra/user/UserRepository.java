package com.depromeet.archive.infra.user;

import com.depromeet.archive.common.exception.ResourceNotFoundException;
import com.depromeet.archive.domain.user.UserReader;
import com.depromeet.archive.domain.user.UserStore;
import com.depromeet.archive.domain.user.entity.User;
import com.depromeet.archive.infra.user.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepository implements UserReader, UserStore {

    private final UserJpaRepository jpaRepository;

    @Override
    public User findUserByMail(String mailAddress) {
        return jpaRepository.findUserByMailAddress(mailAddress);
    }

    @Override
    public User findUserById(long userId) {
        return jpaRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("유저를 찾을 수 없습니다. 아이디:" + userId));
    }

    @Override
    public void saveUser(User user) {
        jpaRepository.save(user);
    }

    @Override
    public void removeUser(User user) {
        jpaRepository.delete(user);
    }

}

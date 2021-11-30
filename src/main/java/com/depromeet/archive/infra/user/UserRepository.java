package com.depromeet.archive.infra.user;

import com.depromeet.archive.common.exception.DuplicateResourceException;
import com.depromeet.archive.common.exception.ResourceNotFoundException;
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
            throw new ResourceNotFoundException("유저를 찾을 수 없습니다. 이메일: " + mailAddress);
        return user;
    }

    @Override
    public User findUserById(long userId) {
        return jpaRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("유저를 찾을 수 없습니다. 아이디:" + userId));
    }

    @Override
    public void saveUser(User user) {
        try {
            jpaRepository.saveAndFlush(user);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateResourceException("이미 존재하는 이메일 주소입니다: " + user.getMailAddress());
        }
    }

    @Override
    public void removeUser(User user) {
        jpaRepository.delete(user);
    }
}

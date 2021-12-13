package com.depromeet.archive.domain.user;

import com.depromeet.archive.domain.user.command.BasicRegisterCommand;
import com.depromeet.archive.domain.user.entity.BaseUser;
import com.depromeet.archive.exception.common.DuplicateResourceException;
import com.depromeet.archive.infra.user.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean isDuplicatedEmail(String email) {
        return userRepository.findUserByMailAddress(email).isPresent();
    }

    public long getOrRegisterUser(BasicRegisterCommand registerCommand) {
        userRepository.save(registerCommand.toUserEntity());
        BaseUser user = userRepository.findUserByMailAddress(registerCommand.getEmail()).orElse(null);
        if (user == null) {
            user = registerCommand.toUserEntity();
            user = userRepository.save(user);
        }
        return user.getUserId();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public long registerUser(BasicRegisterCommand command) {
        Optional<BaseUser> user = userRepository.findUserByMailAddress(command.getEmail());
        if (user.isPresent())
            throw new DuplicateResourceException("이미 등록된 유저가 존재합니다");
        BaseUser newUser = command.toUserEntity();
        userRepository.save(newUser);
        long registeredId = newUser.getUserId();
        log.info("유저 회원가입, 아이디: {}, 이메일: {}", registeredId, newUser.getMailAddress());
        return newUser.getUserId();
    }

    public void deleteUser(long userId) {
        BaseUser user = userRepository.getById(userId);
        userRepository.delete(user);
    }

}

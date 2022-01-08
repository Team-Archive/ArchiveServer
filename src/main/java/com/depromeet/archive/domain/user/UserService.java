package com.depromeet.archive.domain.user;

import com.depromeet.archive.api.dto.user.BaseUserDto;
import com.depromeet.archive.domain.user.command.BasicRegisterCommand;
import com.depromeet.archive.domain.user.entity.BaseUser;
import com.depromeet.archive.exception.common.DuplicateResourceException;
import com.depromeet.archive.infra.user.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
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

    public BaseUserDto registerUser(BasicRegisterCommand command) {
        try {
            BaseUser newUser = command.toUserEntity();
            userRepository.saveAndFlush(newUser);
            return BaseUserDto.from(newUser);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("이메일이 이미 존재합니다.");
        }
    }

    public void deleteUser(long userId) {
        BaseUser user = userRepository.getById(userId);
        userRepository.delete(user);
    }

}

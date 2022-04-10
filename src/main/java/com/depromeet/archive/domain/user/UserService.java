package com.depromeet.archive.domain.user;

import com.depromeet.archive.api.dto.user.BaseUserDto;
import com.depromeet.archive.domain.user.command.BasicRegisterCommand;
import com.depromeet.archive.domain.user.entity.BaseUser;
import com.depromeet.archive.domain.user.info.UserInfo;
import com.depromeet.archive.exception.common.DuplicateResourceException;
import com.depromeet.archive.infra.user.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean isDuplicatedEmail(String email) {
        return userRepository.findByMailAddress(email).isPresent();
    }

    public long getOrRegisterUser(BasicRegisterCommand registerCommand) {
        var user = userRepository.findByMailAddress(registerCommand.getEmail())
                .orElseGet(() -> userRepository.save(registerCommand.toUserEntity()));
        return user.getUserId();
    }

    public UserInfo getOrRegisterUserReturnInfo(BasicRegisterCommand registerCommand) {
        var user = userRepository.findByMailAddress(registerCommand.getEmail())
                .orElseGet(() -> userRepository.save(registerCommand.toUserEntity()));
        return new UserInfo(user.getMailAddress(), user.getRole(), user.getUserId());
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

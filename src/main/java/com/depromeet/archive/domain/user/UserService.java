package com.depromeet.archive.domain.user;

import com.depromeet.archive.api.dto.user.BaseUserDto;
import com.depromeet.archive.domain.common.MessagingService;
import com.depromeet.archive.domain.user.command.BasicRegisterCommand;
import com.depromeet.archive.domain.user.command.OAuthRegisterCommand;
import com.depromeet.archive.domain.user.entity.BaseUser;
import com.depromeet.archive.domain.user.entity.PasswordUser;
import com.depromeet.archive.domain.user.info.UserInfo;
import com.depromeet.archive.exception.common.DuplicateResourceException;
import com.depromeet.archive.exception.common.ResourceNotFoundException;
import com.depromeet.archive.exception.user.OAuthUserHasNotPasswordException;
import com.depromeet.archive.infra.mail.MailService;
import com.depromeet.archive.infra.user.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MessagingService messagingService;
    private final PasswordEncoder encoder;
    private final MailService mailService;

    public boolean existsEmail(String email) {
        return userRepository.findByMailAddress(email).isPresent();
    }

    public long getOrRegisterUser(BasicRegisterCommand registerCommand) {
        var user = userRepository.findByMailAddress(registerCommand.getEmail())
                .orElseGet(() -> registerUser(registerCommand));
        return user.getUserId();
    }

    public UserInfo getOrRegisterUserReturnInfo(BasicRegisterCommand registerCommand) {
        var user = userRepository.findByMailAddress(registerCommand.getEmail())
                .orElseGet(() -> registerUser(registerCommand));
        return user.convertToUserInfo();
    }

    public BaseUser registerUser(BasicRegisterCommand registerCommand) {
        try {
            var user = userRepository.save(registerCommand.toUserEntity());
            sendRegisterNotification(registerCommand, user);
            return user;
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("이메일이 이미 존재합니다.");
        }
    }

    public void updateTemporaryPassword(final String email, final String temporaryPassword) {
        var passwordUser = userRepository.findByMailAddress(email)
                .map(this::convertPasswordUser)
                .orElseThrow(() -> new ResourceNotFoundException("Email"));
        passwordUser.setTemporaryPassword(encoder.encode(temporaryPassword));
        mailService.sendTemporaryPassword(email, temporaryPassword);
    }

    public void deleteUser(long userId) {
        BaseUser user = userRepository.getById(userId);
        userRepository.delete(user);
    }

    private void sendRegisterNotification(BasicRegisterCommand registerCommand, BaseUser user) {
        if (registerCommand instanceof OAuthRegisterCommand) {
            var oAuthRegisterCommand = (OAuthRegisterCommand) registerCommand;
            var oauthProvider = oAuthRegisterCommand.getProvider().getRegistrationId();
            messagingService.sendUserRegisterMessage(BaseUserDto.from(user), oauthProvider);
        } else {
            messagingService.sendUserRegisterMessage(BaseUserDto.from(user), "Id/Password");
        }
    }

    private PasswordUser convertPasswordUser(BaseUser user) {
        if (!(user instanceof PasswordUser)) {
            throw new OAuthUserHasNotPasswordException();
        }
        return (PasswordUser) user;
    }

}

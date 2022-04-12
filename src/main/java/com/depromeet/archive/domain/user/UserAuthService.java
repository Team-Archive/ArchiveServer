package com.depromeet.archive.domain.user;

import com.depromeet.archive.domain.user.command.LoginCommand;
import com.depromeet.archive.domain.user.entity.BaseUser;
import com.depromeet.archive.domain.user.entity.PasswordUser;
import com.depromeet.archive.domain.user.info.UserInfo;
import com.depromeet.archive.exception.common.ResourceNotFoundException;
import com.depromeet.archive.exception.user.LoginFailException;
import com.depromeet.archive.exception.user.OAuthUserHasNotPasswordException;
import com.depromeet.archive.infra.mail.MailService;
import com.depromeet.archive.infra.user.jpa.PasswordUserRepository;
import com.depromeet.archive.infra.user.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserAuthService {

    private final UserRepository userRepository;
    private final PasswordUserRepository passwordUserRepository;
    private final PasswordEncoder encoder;
    private final MailService mailService;

    public UserInfo tryLoginAndReturnInfo(LoginCommand command) throws LoginFailException {
        var user = verifyPasswordReturnUser(command.getEmail(), command.getPassword());
        return user.convertToUserInfo();
    }

    public PasswordUser verifyPasswordReturnUser(final String email, final String password) {
        var user = passwordUserRepository.findPasswordUserByMailAddress(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email"));
        if (!encoder.matches(password, user.getPassword()))
            throw new LoginFailException("비밀번호가 다릅니다");
        return user;
    }

    @Transactional
    public void updateTemporaryPassword(final String email, final String temporaryPassword) {
        var passwordUser = userRepository.findByMailAddress(email)
                .map(this::convertPasswordUser)
                .orElseThrow(() -> new ResourceNotFoundException("Email"));
        passwordUser.setTemporaryPassword(encoder.encode(temporaryPassword));
        mailService.sendTemporaryPassword(email, temporaryPassword);
    }

    private PasswordUser convertPasswordUser(BaseUser user) {
        if (!(user instanceof PasswordUser)) {
            throw new OAuthUserHasNotPasswordException();
        }
        return (PasswordUser) user;
    }

}

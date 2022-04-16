package com.depromeet.archive.domain.user;

import com.depromeet.archive.domain.user.entity.BaseUser;
import com.depromeet.archive.domain.user.entity.PasswordUser;
import com.depromeet.archive.exception.common.ResourceNotFoundException;
import com.depromeet.archive.exception.user.OAuthUserHasNotPasswordException;
import com.depromeet.archive.infra.mail.MailService;
import com.depromeet.archive.infra.user.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final MailService mailService;

    public boolean existsEmail(String email) {
        return userRepository.findByMailAddress(email).isPresent();
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

    private PasswordUser convertPasswordUser(BaseUser user) {
        if (!(user instanceof PasswordUser)) {
            throw new OAuthUserHasNotPasswordException();
        }
        return (PasswordUser) user;
    }

}

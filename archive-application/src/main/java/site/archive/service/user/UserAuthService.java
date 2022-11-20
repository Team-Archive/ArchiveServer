package site.archive.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.archive.common.exception.common.ResourceNotFoundException;
import site.archive.common.exception.common.UnauthorizedResourceException;
import site.archive.common.exception.user.LoginFailException;
import site.archive.common.exception.user.OAuthUserHasNotPasswordException;
import site.archive.domain.user.BaseUser;
import site.archive.domain.user.PasswordUser;
import site.archive.domain.user.PasswordUserRepository;
import site.archive.domain.user.UserInfo;
import site.archive.domain.user.UserRepository;
import site.archive.dto.v1.auth.LoginCommandV1;
import site.archive.dto.v1.user.UserPasswordResetRequestDtoV1;
import site.archive.infra.mail.MailService;

import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserAuthService {

    private final UserRepository userRepository;
    private final PasswordUserRepository passwordUserRepository;
    private final PasswordEncoder encoder;
    private final MailService mailService;

    public UserInfo tryLoginAndReturnInfo(LoginCommandV1 command) throws LoginFailException {
        var user = verifyPasswordReturnUser(command.getEmail(), command.getPassword());
        return user.convertToUserInfo();
    }

    @Transactional
    public void updateTemporaryPassword(final String email, final String temporaryPassword) {
        var passwordUser = userRepository.findByMailAddress(email)
                                         .map(this::convertPasswordUser)
                                         .orElseThrow(() -> new ResourceNotFoundException("Email"));
        passwordUser.updatePassword(encoder.encode(temporaryPassword), true);
        mailService.sendTemporaryPassword(email, temporaryPassword);
    }

    @Transactional
    public void resetPassword(UserInfo userInfo, UserPasswordResetRequestDtoV1 userPasswordResetRequestDtoV1) {
        if (!Objects.equals(userInfo.getMailAddress(), userPasswordResetRequestDtoV1.getEmail())) {
            throw new UnauthorizedResourceException("해당 이메일에 대한 비밀번호 초기화 권한이 없습니다.");
        }
        this.resetPassword(userPasswordResetRequestDtoV1);
    }

    @Transactional
    public void resetPassword(UserPasswordResetRequestDtoV1 userPasswordResetRequestDtoV1) {
        var passwordUser = verifyPasswordReturnUser(
            userPasswordResetRequestDtoV1.getEmail(), userPasswordResetRequestDtoV1.getCurrentPassword());
        passwordUser.updatePassword(encoder.encode(userPasswordResetRequestDtoV1.getNewPassword()), false);
    }

    public boolean isTemporaryPasswordLogin(long userId) {
        return passwordUserRepository.findById(userId)
                                     .map(PasswordUser::isCurrentTemporaryPassword)
                                     .orElse(false);
    }

    private PasswordUser verifyPasswordReturnUser(final String email, final String password) {
        var user = passwordUserRepository.findByMailAddress(email)
                                         .orElseThrow(() -> new ResourceNotFoundException("Email"));
        if (!encoder.matches(password, user.getPassword())) {
            throw new LoginFailException("비밀번호가 다릅니다");
        }
        return user;
    }

    private PasswordUser convertPasswordUser(BaseUser user) {
        if (!(user instanceof PasswordUser)) {
            throw new OAuthUserHasNotPasswordException();
        }
        return (PasswordUser) user;
    }

}

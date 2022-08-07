package site.archive.domain.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.archive.api.command.BasicRegisterCommand;
import site.archive.api.command.OAuthRegisterCommand;
import site.archive.api.v1.dto.user.BaseUserDto;
import site.archive.common.exception.common.DuplicateResourceException;
import site.archive.domain.common.MessagingService;
import site.archive.domain.user.entity.BaseUser;
import site.archive.domain.user.info.UserInfo;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserRegisterService {

    private final UserRepository userRepository;
    private final MessagingService messagingService;

    public long getOrRegisterUser(BasicRegisterCommand registerCommand) {
        var user = userRepository.findByMailAddress(registerCommand.getEmail())
                                 .orElseGet(() -> registerUser(registerCommand));
        return user.getId();
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

    private void sendRegisterNotification(BasicRegisterCommand registerCommand, BaseUser user) {
        if (registerCommand instanceof OAuthRegisterCommand oAuthRegisterCommand) {
            var oauthProvider = oAuthRegisterCommand.getProvider().getRegistrationId();
            messagingService.sendUserRegisterMessage(BaseUserDto.from(user), oauthProvider);
        } else {
            messagingService.sendUserRegisterMessage(BaseUserDto.from(user), "Id/Password");
        }
    }

}

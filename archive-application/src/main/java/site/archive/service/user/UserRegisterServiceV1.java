package site.archive.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.archive.common.exception.common.DuplicateResourceException;
import site.archive.domain.user.BaseUser;
import site.archive.domain.user.UserInfo;
import site.archive.domain.user.UserRepository;
import site.archive.dto.v1.auth.BasicRegisterCommandV1;
import site.archive.dto.v1.auth.OAuthRegisterCommandV1;
import site.archive.dto.v1.user.BaseUserDto;
import site.archive.service.message.MessagingService;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserRegisterServiceV1 {

    private final UserRepository userRepository;
    private final MessagingService messagingService;

    public long getOrRegisterUser(BasicRegisterCommandV1 registerCommand) {
        var user = userRepository.findByMailAddress(registerCommand.getEmail())
                                 .orElseGet(() -> registerUser(registerCommand));
        return user.getId();
    }

    public UserInfo getOrRegisterUserReturnInfo(BasicRegisterCommandV1 registerCommand) {
        var user = userRepository.findByMailAddress(registerCommand.getEmail())
                                 .orElseGet(() -> registerUser(registerCommand));
        return user.convertToUserInfo();
    }

    public BaseUser registerUser(BasicRegisterCommandV1 registerCommand) {
        try {
            var user = userRepository.save(registerCommand.toUserEntity());
            sendRegisterNotification(registerCommand, user);
            return user;
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("이메일이 이미 존재합니다.");
        }
    }

    private void sendRegisterNotification(BasicRegisterCommandV1 registerCommand, BaseUser user) {
        if (registerCommand instanceof OAuthRegisterCommandV1 oAuthRegisterCommand) {
            var oauthProvider = oAuthRegisterCommand.getProvider().getRegistrationId();
            messagingService.sendUserRegisterMessage(BaseUserDto.from(user), oauthProvider);
        } else {
            messagingService.sendUserRegisterMessage(BaseUserDto.from(user), "Id/Password");
        }
    }

}

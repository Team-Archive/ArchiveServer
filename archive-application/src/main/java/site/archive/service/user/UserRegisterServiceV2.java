package site.archive.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.archive.common.exception.common.DuplicateResourceException;
import site.archive.domain.user.PasswordUser;
import site.archive.domain.user.UserInfo;
import site.archive.domain.user.UserRepository;
import site.archive.dto.v1.user.BaseUserDtoV1;
import site.archive.dto.v2.OAuthRegisterRequestDto;
import site.archive.dto.v2.PasswordRegisterRequestDto;
import site.archive.service.message.MessagingService;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserRegisterServiceV2 {

    private final UserRepository userRepository;
    private final MessagingService messagingService;

    public UserInfo registerUser(PasswordRegisterRequestDto registerRequest) {
        try {
            var user = userRepository.save(registerRequest.toUserEntity());
            messagingService.sendUserRegisterMessage(BaseUserDtoV1.from(user), PasswordUser.PASSWORD_TYPE);
            return user.convertToUserInfo();
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("이메일 또는 닉네임이 중복되었습니다. 중복을 다시 확인해주세요.");
        }
    }

    public UserInfo registerUser(OAuthRegisterRequestDto registerCommand) {
        try {
            var user = userRepository.save(registerCommand.toUserEntity());
            var oauthProvider = registerCommand.getProvider().getRegistrationId();
            messagingService.sendUserRegisterMessage(BaseUserDtoV1.from(user), oauthProvider);
            return user.convertToUserInfo();
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("이메일 또는 닉네임이 중복되었습니다. 중복을 다시 확인해주세요.");
        }
    }

}

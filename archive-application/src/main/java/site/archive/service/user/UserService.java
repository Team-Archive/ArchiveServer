package site.archive.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.archive.common.exception.common.DuplicateFieldValueException;
import site.archive.common.exception.common.ResourceNotFoundException;
import site.archive.domain.user.OAuthUser;
import site.archive.domain.user.OAuthUserRepository;
import site.archive.domain.user.PasswordUserRepository;
import site.archive.domain.user.UserRepository;
import site.archive.dto.v1.user.BaseUserDto;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordUserRepository passwordUserRepository;
    private final OAuthUserRepository oAuthUserRepository;

    public BaseUserDto findUserById(long userId) {
        return userRepository.findById(userId)
                             .map(BaseUserDto::from)
                             .orElseThrow(() -> new ResourceNotFoundException("아이디에 해당하는 유저가 존재하지 않습니다."));
    }

    public BaseUserDto findSpecificUserById(long userId) {
        var user = findUserById(userId);
        updateUserTypeWithProviderWhenOAuthUser(userId, user);
        return user;
    }

    public boolean existsEmail(String email) {
        return userRepository.findByMailAddress(email).isPresent();
    }

    public boolean existsNickname(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    @Transactional
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public void updateUserNickname(long userId, String nickname) {
        if (existsNickname(nickname)) {
            throw new DuplicateFieldValueException("nickname", nickname);
        }
        userRepository.updateNickName(userId, nickname);
    }

    private void updateUserTypeWithProviderWhenOAuthUser(long userId, BaseUserDto user) {
        if (OAuthUser.OAUTH_TYPE.equals(user.getUserType())) {
            var oAuthType = oAuthUserRepository.findById(userId)
                                               .map(oAuthUser -> oAuthUser.getOAuthProvider().toString())
                                               .orElseThrow(() -> new ResourceNotFoundException("아이디에 해당하는 OAuth 유저가 존재하지 않습니다."));
            user.updateSpecificUserType(oAuthType);
        }
    }

}

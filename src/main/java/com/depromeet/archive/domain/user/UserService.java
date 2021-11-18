package com.depromeet.archive.domain.user;

import com.depromeet.archive.common.exception.ResourceNotFoundException;
import com.depromeet.archive.domain.user.command.BasicRegisterCommand;
import com.depromeet.archive.domain.user.command.CredentialRegisterCommand;
import com.depromeet.archive.domain.user.command.LoginCommand;
import com.depromeet.archive.domain.user.entity.User;
import com.depromeet.archive.domain.user.info.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserReader userReader;
    private final UserStore userStore;
    private final StringEncryptor encryptor;

    public boolean checkEmailDuplicate(String email) {
        try {
            userReader.findUserByMail(email);
            return false;
        } catch (ResourceNotFoundException e) {
            return true;
        }
    }

    public long updateNonCredentialUser(BasicRegisterCommand registerInfo) {
        try {
            User foundUser = userReader.findUserByMail(registerInfo.getEmail());
            return foundUser.getUserId();
        } catch (ResourceNotFoundException exception) {
            User newUser = User.fromRegisterCommand(registerInfo);
            userStore.saveUser(newUser);
            return newUser.getUserId();
        }
    }

    public UserInfo tryLoginAndReturnInfo(LoginCommand loginRequest) {
        User user = userReader.findUserByMail(loginRequest.getEmail());
        String encryptedPassword = encryptor.encrypt(loginRequest.getPassword());
        user.tryLogin(encryptedPassword);
        return user.getUserInfo();
    }

    public void registerUser(CredentialRegisterCommand command) {
        String unencryptedPassword = command.getPassword();
        String encrypted = encryptor.encrypt(unencryptedPassword);
        command.setPassword(encrypted);
        User user = User.fromCredentialRegisterCommand(command);
        userStore.saveUser(user);
    }

    public void deleteUser(long userId) {
        User user = userReader.findUserById(userId);
        userStore.removeUser(user);
        log.info("유저 탈퇴: {}", user.getMailAddress());
    }

}

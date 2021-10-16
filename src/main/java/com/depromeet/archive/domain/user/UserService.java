package com.depromeet.archive.domain.user;

import com.depromeet.archive.common.exception.DuplicateResourceException;
import com.depromeet.archive.common.exception.ResourceNotFoundException;
import com.depromeet.archive.domain.user.command.BasicRegisterCommand;
import com.depromeet.archive.domain.user.command.CredentialRegisterCommand;
import com.depromeet.archive.domain.user.command.LoginCommand;
import com.depromeet.archive.domain.user.entity.User;
import com.depromeet.archive.domain.user.info.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserReader userReader;

    @Autowired
    private UserStore userStore;

    @Autowired
    private StringEncryptor encryptor;

    public boolean checkEmailDuplicate(String email) {
        try {
            userReader.findUserByMail(email);
            return true;
        } catch (ResourceNotFoundException e) {
            return false;
        }
    }

    public long updateNonCredentialUser(BasicRegisterCommand registerInfo) {
        try {
            User foundUser = userReader.findUserByMail(registerInfo.getMailAddress());
            return foundUser.getUserId();
        } catch (ResourceNotFoundException exception) {
            User newUser = User.fromRegisterCommand(registerInfo);
            userStore.saveUser(newUser);
            return newUser.getUserId();
        }
    }

    public UserInfo tryLoginAndReturnInfo(LoginCommand loginRequest) {
        User user = userReader.findUserByMail(loginRequest.getMailAddress());
        String encryptedPassword = encryptor.encrypt(loginRequest.getPassword());
        user.tryLogin(encryptedPassword);
        return user.getUserInfo();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void registerUser(CredentialRegisterCommand command) {
        if (checkEmailDuplicate(command.getMailAddress()))
            throw new DuplicateResourceException("이미 가입된 메일입니다: " + command.getMailAddress());
        String unencryptedPassword = command.getCredential();
        String encrypted = encryptor.encrypt(unencryptedPassword);
        command.setCredential(encrypted);
        User user = User.fromCredentialRegisterCommand(command);
        userStore.saveUser(user);
    }

    public void deleteUser(long userId) {
        User user = userReader.findUserById(userId);
        userStore.removeUser(user);
    }
}

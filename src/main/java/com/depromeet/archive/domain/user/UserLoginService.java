package com.depromeet.archive.domain.user;

import com.depromeet.archive.domain.user.command.LoginCommand;
import com.depromeet.archive.domain.user.entity.PasswordUser;
import com.depromeet.archive.domain.user.info.UserInfo;
import com.depromeet.archive.exception.user.LoginFailException;
import com.depromeet.archive.infra.user.jpa.PasswordUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final PasswordUserRepository repository;
    private final PasswordEncoder encoder;

    public UserInfo tryLoginAndReturnInfo(LoginCommand command) throws LoginFailException {
        PasswordUser user = repository.findPasswordUserByMailAddress(command.getEmail());
        String encryptedPassword = user.getPassword();
        if (!encoder.matches(command.getPassword(), encryptedPassword))
            throw new LoginFailException("로그인에 실패하였습니다. 비밀번호가 다릅니다.");
        return user.getUserInfo();
    }

}

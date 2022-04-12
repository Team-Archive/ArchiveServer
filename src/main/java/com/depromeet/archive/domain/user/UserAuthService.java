package com.depromeet.archive.domain.user;

import com.depromeet.archive.domain.user.command.LoginCommand;
import com.depromeet.archive.domain.user.entity.PasswordUser;
import com.depromeet.archive.domain.user.info.UserInfo;
import com.depromeet.archive.exception.common.ResourceNotFoundException;
import com.depromeet.archive.exception.user.LoginFailException;
import com.depromeet.archive.infra.user.jpa.PasswordUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserAuthService {

    private final PasswordUserRepository repository;
    private final PasswordEncoder encoder;

    public UserInfo tryLoginAndReturnInfo(LoginCommand command) throws LoginFailException {
        var user = verifyPasswordReturnUser(command.getEmail(), command.getPassword());
        return user.convertToUserInfo();
    }

    public PasswordUser verifyPasswordReturnUser(final String email, final String password) {
        var user = repository.findPasswordUserByMailAddress(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email"));
        if (!encoder.matches(password, user.getPassword()))
            throw new LoginFailException("비밀번호가 다릅니다");
        return user;
    }

}

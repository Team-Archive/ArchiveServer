package com.depromeet.archive.domain.user.entity;

import com.depromeet.archive.exception.user.LoginFailException;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@Embeddable
public class UserCredential {

    @Column(name = "password")
    private String password;
    @Column(name = "use_credential")
    private boolean useCredential;

    public UserCredential(String password, boolean useCredential) {
        this.password = password;
        this.useCredential = useCredential;
    }

    public boolean matches(String passwordToCheck) {
        if (!useCredential)
            throw new LoginFailException("비밀번호를 지원하지 않는 아이디입니다");
        return password.matches(passwordToCheck);
    }
}

package com.depromeet.archive.domain.user.entity;

import com.depromeet.archive.domain.common.BaseTimeEntity;
import com.depromeet.archive.domain.user.command.BasicRegisterCommand;
import com.depromeet.archive.domain.user.command.CredentialRegisterCommand;
import com.depromeet.archive.domain.user.exception.LoginFailException;
import com.depromeet.archive.domain.user.info.UserInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Entity
@Table(name = "user")
@Access(AccessType.FIELD)
public class User extends BaseTimeEntity {

    @Setter
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private long userId;

    @NonNull
    @Column(name = "mail_address", unique = true)
    private String mailAddress;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole role;

    @NonNull
    @Embedded
    private UserCredential credential;

    public void tryLogin(String requestedPassword) {
        boolean result = credential.matches(requestedPassword);
        if (!result)
            throw new LoginFailException("비밀번호가 일치하지 않습니다");
    }

    public UserInfo getUserInfo() {
        return new UserInfo(mailAddress, role, userId);
    }

    public static User fromRegisterCommand(BasicRegisterCommand request) {
        return new User(request.getEmail(), UserRole.GENERAL, new UserCredential("", false));
    }

    public static User fromCredentialRegisterCommand(CredentialRegisterCommand credentialRegisterCommand) {
        return new User(credentialRegisterCommand.getEmail(), UserRole.GENERAL,
                new UserCredential(credentialRegisterCommand.getPassword(), true));
    }

}

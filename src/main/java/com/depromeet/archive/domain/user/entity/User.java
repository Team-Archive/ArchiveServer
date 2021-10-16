package com.depromeet.archive.domain.user.entity;

import com.depromeet.archive.domain.user.command.BasicRegisterCommand;
import com.depromeet.archive.domain.user.command.CredentialRegisterCommand;
import com.depromeet.archive.domain.user.exception.LoginFailException;
import com.depromeet.archive.domain.user.info.UserInfo;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Entity
@Table(name = "user_info")
@Access(AccessType.FIELD)
public class User {

    @Setter
    @Id @GeneratedValue
    private long userId;

    @NonNull
    @Column(name = "mail_address", unique = true)
    private String mailAddress;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole role;

    @NonNull
    @Column(name = "user_name")
    private String userName;

    @NonNull
    @Embedded
    private UserCredential credential;

    public void tryLogin(String requestedPassword) {
        boolean result = credential.matches(requestedPassword);
        if (!result)
            throw new LoginFailException("비밀번호가 일치하지 않습니다");
    }

    public UserInfo getUserInfo() {
        return new UserInfo(mailAddress, role, userId, userName);
    }

    public static User fromRegisterCommand(BasicRegisterCommand request) {
        return new User(request.getMailAddress(), UserRole.GENERAL, request.getUserName(), new UserCredential("", false));
    }

    public static User fromCredentialRegisterCommand(CredentialRegisterCommand credentialRegisterCommand) {
        return new User(credentialRegisterCommand.getMailAddress(), UserRole.GENERAL, credentialRegisterCommand.getUserName(),
                new UserCredential(credentialRegisterCommand.getCredential(), true));
    }
}
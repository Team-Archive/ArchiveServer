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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Entity
@Table(name = "user")
@Access(AccessType.FIELD)
@SQLDelete(sql = "UPDATE user SET is_deleted = true WHERE user_id=?")
@Where(clause = "is_deleted = false")
public class User extends BaseTimeEntity {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId = null;

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

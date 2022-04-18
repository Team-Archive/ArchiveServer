package com.depromeet.archive.domain.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("password")
@NoArgsConstructor
@Table(name= "password_user")
public class PasswordUser extends BaseUser {

    @Getter
    @Column(name= "password")
    private String password;

    @Column(name = "is_temporary_password", columnDefinition = "boolean default false")
    private Boolean isTemporaryPassword;


    public PasswordUser(String mailAddress, UserRole role, String password) {
        super(mailAddress, role);
        this.password = password;
    }

    public void updatePassword(final String password, final boolean isTemporaryPassword) {
        this.password = password;
        this.isTemporaryPassword = isTemporaryPassword;
    }

}

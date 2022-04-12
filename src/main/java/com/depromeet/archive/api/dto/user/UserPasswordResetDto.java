package com.depromeet.archive.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordResetDto {

    @Email
    private String email;
    @NotNull
    private String currentPassword;
    @NotNull
    private String newPassword;

}

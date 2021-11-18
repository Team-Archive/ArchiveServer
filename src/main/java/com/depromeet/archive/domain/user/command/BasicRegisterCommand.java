package com.depromeet.archive.domain.user.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasicRegisterCommand {

    @NotEmpty(message = "필수 입력 항목입니다.")
    @Email(message = "올바른 이메일을 입력해 주세요.")
    private String email;
}

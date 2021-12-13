package com.depromeet.archive.domain.user.command;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class CredentialRegisterCommand extends BasicRegisterCommand {

    @NotEmpty(message = "필수 입력 항목입니다.")
    @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9$@$!%*#?&]{8,20}$",
            message = "비밀번호는 특수문자 / 문자 / 숫자 포함 형태의 8~15자리 이내로 입력해 주세요.")
    private String password;

    public CredentialRegisterCommand(String userMail, String password) {
        super(userMail);
        this.password = password;
    }

}

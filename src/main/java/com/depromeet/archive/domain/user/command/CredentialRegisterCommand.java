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
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$",
            message = "비밀번호는 영어, 숫자를 하나 이상 포함해 8~20자 범위로 입력해주세요")
    private String credential;

    public CredentialRegisterCommand(String userMail, String credential) {
        super(userMail);
        this.credential = credential;
    }

}

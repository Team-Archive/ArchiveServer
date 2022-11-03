package site.archive.dto.v2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.archive.domain.user.BaseUser;
import site.archive.domain.user.PasswordUser;
import site.archive.domain.user.UserRole;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRegisterRequestDto {

    @NotEmpty(message = "이메일은 필수 값입니다.")
    @Email(message = "올바른 이메일을 입력해 주세요.")
    private String email;

    @NotEmpty(message = "패스워드는 필수 값입니다.")
    @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9@$!%*#?&]{8,20}$",
             message = "비밀번호는 영문/숫자 를 꼭 포함하여 8~20자리로 입력해 주세요.")
    private String password;

    @NotEmpty(message = "닉네임은 필수 값입니다.")
    private String nickname;

    public BaseUser toUserEntity() {
        return new PasswordUser(this.email, UserRole.GENERAL, this.password, this.nickname);
    }

    public void updatePasswordToEncrypt(String encryptPassword) {
        this.password = encryptPassword;
    }

}

package site.archive.dto.v1.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordResetRequestDto {

    @Email
    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotEmpty
    private String currentPassword;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9@$!%*#?&]{8,20}$",
             message = "비밀번호는 영문/숫자 를 꼭 포함하여 8~20자리로 입력해 주세요.")
    private String newPassword;

}

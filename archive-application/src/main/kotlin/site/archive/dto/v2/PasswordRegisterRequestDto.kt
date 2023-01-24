package site.archive.dto.v2

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import site.archive.domain.user.BaseUser
import site.archive.domain.user.PasswordUser
import site.archive.domain.user.UserRole

data class PasswordRegisterRequestDto(
    @NotBlank(message = "이메일은 필수 값입니다.")
    @Email(message = "올바른 이메일을 입력해 주세요.")
    val email: String,

    @NotBlank(message = "닉네임은 필수 값입니다.")
    val nickname: String,

    @NotBlank(message = "패스워드는 필수 값입니다.")
    @Pattern(
        regexp = "(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d@$!%*#?&]{8,20}$",
        message = "비밀번호는 영문/숫자 를 꼭 포함하여 8~20자리로 입력해 주세요."
    )
    var password: String
) {

    fun toUserEntity(): BaseUser {
        return PasswordUser(email, UserRole.GENERAL, password, nickname)
    }

    fun updatePasswordToEncrypt(encryptPassword: String) {
        password = encryptPassword
    }

}
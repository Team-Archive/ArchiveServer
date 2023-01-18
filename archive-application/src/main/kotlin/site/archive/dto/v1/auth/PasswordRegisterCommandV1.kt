package site.archive.dto.v1.auth

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import site.archive.common.extractIdFromMail
import site.archive.domain.user.BaseUser
import site.archive.domain.user.PasswordUser
import site.archive.domain.user.UserRole

class PasswordRegisterCommandV1(
    email: String,
    @field: NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @field: Pattern(
        regexp = "(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9@$!%*#?&]{8,20}$",
        message = "비밀번호는 영문/숫자 를 꼭 포함하여 8~20자리로 입력해 주세요."
    )
    var password: String
) : BasicRegisterCommandV1(email) {

    override fun toUserEntity(): BaseUser {
        return PasswordUser(
            email,
            UserRole.GENERAL,
            password,
            extractIdFromMail(email)
        )
    }

}
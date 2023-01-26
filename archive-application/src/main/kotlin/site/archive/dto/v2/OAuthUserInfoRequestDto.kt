package site.archive.dto.v2

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class OAuthUserInfoRequestDto(
    @NotBlank(message = "Oauth provider는 필수 값입니다.")
    val provider: String,

    @NotBlank(message = "Provider access token은 필수 값입니다.")
    @JsonProperty("providerAccessToken")
    val token: String,

    @NotBlank(message = "닉네임은 필수 값입니다.")
    val nickname: String
)
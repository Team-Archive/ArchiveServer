package site.archive.dto.v1.user

import com.fasterxml.jackson.annotation.JsonProperty

data class OAuthRegisterRequestDtoV1(
    val provider: String,

    @field: JsonProperty("providerAccessToken")
    val token: String
)
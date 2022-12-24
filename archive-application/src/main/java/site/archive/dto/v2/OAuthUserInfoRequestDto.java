package site.archive.dto.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.validation.constraints.NotEmpty;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OAuthUserInfoRequestDto {

    @NotEmpty(message = "Oauth provider는 필수 값입니다.")
    private String provider;

    @NotEmpty(message = "Provider access token은 필수 값입니다.")
    @JsonProperty("providerAccessToken")
    private String token;

    @NotEmpty(message = "닉네임은 필수 값입니다.")
    private String nickname;

}
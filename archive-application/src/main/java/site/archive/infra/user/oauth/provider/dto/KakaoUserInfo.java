package site.archive.infra.user.oauth.provider.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class KakaoUserInfo {

    private String id;
    private Map<String, String> properties;

    @JsonProperty(value = "kakao_account")
    private KakaoAccount kakaoAccount;

    public String getEmail() {
        return kakaoAccount.getEmail();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class KakaoAccount {

        private String email;
        private Map<String, String> profile;

    }

}

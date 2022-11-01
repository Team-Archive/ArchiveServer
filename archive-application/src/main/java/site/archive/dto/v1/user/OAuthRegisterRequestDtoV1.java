package site.archive.dto.v1.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OAuthRegisterRequestDtoV1 {

    @NotNull
    private String provider;

    @JsonProperty("providerAccessToken")
    private String token;

}

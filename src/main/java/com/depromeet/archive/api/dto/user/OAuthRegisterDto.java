package com.depromeet.archive.api.dto.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OAuthRegisterDto {

    private static final String DEFAULT_PROVIDER = "apple";

    private String provider = DEFAULT_PROVIDER;

    @JsonAlias({"providerAccessToken", "id_token"})
    private String token;

    private String state;
    private String code;
    private String user;

}

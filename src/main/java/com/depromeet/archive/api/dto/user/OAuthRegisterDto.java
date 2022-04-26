package com.depromeet.archive.api.dto.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OAuthRegisterDto {

    @NotNull
    private String provider;

    @JsonAlias({"providerAccessToken", "id_token"})
    private String token;

}

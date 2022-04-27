package com.depromeet.archive.api.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("providerAccessToken")
    private String token;

}

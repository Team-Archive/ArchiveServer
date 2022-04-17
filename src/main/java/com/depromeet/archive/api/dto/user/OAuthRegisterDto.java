package com.depromeet.archive.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OAuthRegisterDto {

    private String providerAccessToken;

    @NotNull
    private String provider;

}

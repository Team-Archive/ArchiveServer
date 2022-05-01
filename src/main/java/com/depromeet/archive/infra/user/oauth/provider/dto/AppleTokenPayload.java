package com.depromeet.archive.infra.user.oauth.provider.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AppleTokenPayload {

    private String iss;
    private String aud;
    private Long exp;
    private Long iat;
    private String sub;
    private String nonce;
    private String email;

    @JsonProperty("c_hash")
    private String cHash;
    @JsonProperty("email_verified")
    private String emailVerified;
    @JsonProperty("is_private_email")
    private String isPrivateEmail;
    @JsonProperty("auth_time")
    private Long authTime;
    @JsonProperty("nonce_supported")
    private boolean nonceSupported;

}
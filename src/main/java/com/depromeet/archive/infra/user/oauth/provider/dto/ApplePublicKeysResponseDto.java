package com.depromeet.archive.infra.user.oauth.provider.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ApplePublicKeysResponseDto {

    private List<Key> keys;

    @Getter
    public static class Key {
        private String kty;
        private String kid;
        private String use;
        private String alg;
        private String n;
        private String e;
    }

}
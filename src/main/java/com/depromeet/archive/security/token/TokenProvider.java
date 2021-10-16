package com.depromeet.archive.security.token;

import com.depromeet.archive.security.result.AuthToken;

public interface TokenProvider {
    public String createToken(AuthToken token);
    public AuthToken parseUserInfoFromToken(String tokenStr);
}

package com.depromeet.archive.security.login;

import com.depromeet.archive.security.vo.AuthToken;

public interface TokenProvider {
    public String createToken(AuthToken token);
    public AuthToken parseUserInfoFromToken(String tokenStr);
}

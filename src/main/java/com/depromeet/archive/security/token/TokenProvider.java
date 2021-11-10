package com.depromeet.archive.security.token;

import com.depromeet.archive.security.result.AuthToken;

public interface TokenProvider {

    String createToken(AuthToken token);

    AuthToken parseUserInfoFromToken(String tokenStr);

}

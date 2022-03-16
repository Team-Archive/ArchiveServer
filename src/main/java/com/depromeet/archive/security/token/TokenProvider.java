package com.depromeet.archive.security.token;

import com.depromeet.archive.domain.user.info.UserInfo;

public interface TokenProvider {
    String createToken(UserInfo token);

    UserInfo parseUserInfoFromToken(String tokenStr);
}

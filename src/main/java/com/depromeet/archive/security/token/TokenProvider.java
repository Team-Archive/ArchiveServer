package com.depromeet.archive.security.token;

import com.depromeet.archive.domain.user.info.UserInfo;

public interface TokenProvider {
    public String createToken(UserInfo token);
    public UserInfo parseUserInfoFromToken(String tokenStr);
}

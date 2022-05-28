package site.archive.security.token;

import site.archive.domain.user.info.UserInfo;

public interface TokenProvider {
    String createToken(UserInfo token);

    UserInfo parseUserInfoFromToken(String tokenStr);
}

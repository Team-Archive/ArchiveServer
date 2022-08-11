package site.archive.web.config.security.token;

import site.archive.domain.user.UserInfo;

public interface TokenProvider {
    String createToken(UserInfo token);

    UserInfo parseUserInfoFromToken(String tokenStr);
}

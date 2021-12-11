package com.depromeet.archive.util;

import com.depromeet.archive.domain.user.info.UserInfo;
import com.depromeet.archive.exception.security.InvalidTokenException;
import com.depromeet.archive.security.token.jwt.JwtAuthenticationToken;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static UserInfo getCurrentUserInfo() {
        try {
            JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            return token.getUserInfo();
        } catch (NullPointerException e) {
            throw new AccessDeniedException("유저 인증 정보가 존재하지 않습니다");
        } catch (ClassCastException e) {
            throw new InvalidTokenException();
        }
    }
}

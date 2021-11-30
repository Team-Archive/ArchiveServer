package com.depromeet.archive.util;

import com.depromeet.archive.common.exception.BaseException;
import com.depromeet.archive.common.exception.ForbiddenActionException;
import com.depromeet.archive.domain.user.info.UserInfo;
import com.depromeet.archive.security.token.jwt.JwtAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static UserInfo getCurrentUserInfo() {
        try {
            JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            return token.getUserInfo();
        } catch (NullPointerException e) {
            throw new ForbiddenActionException("유저 인증 정보가 존재하지 않습니다");
        } catch (ClassCastException e) {
            throw new BaseException("잘못된 인증 토큰입니다. JwtAuthenticationToken 이 필요합니다.");
        }
    }
}

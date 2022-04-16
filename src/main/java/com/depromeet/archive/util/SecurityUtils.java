package com.depromeet.archive.util;

import com.depromeet.archive.domain.user.info.UserInfo;
import com.depromeet.archive.security.token.jwt.JwtAuthenticationToken;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.SecureRandom;

public class SecurityUtils {

    private static final String NUMBER_ALPHABET_STR = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom RANDOM = new SecureRandom();

    private SecurityUtils() {
    }

    public static UserInfo getCurrentUserInfo() {
        try {
            JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            return token.getUserInfo();
        } catch (Exception e) {
            throw new AccessDeniedException("유저 인증 정보가 존재하지 않습니다");
        }
    }

    public static String generateRandomString(int len) {
        var sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(NUMBER_ALPHABET_STR.charAt(RANDOM.nextInt(NUMBER_ALPHABET_STR.length())));
        }
        return sb.toString();
    }

}

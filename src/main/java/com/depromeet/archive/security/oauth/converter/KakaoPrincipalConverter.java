package com.depromeet.archive.security.oauth.converter;


import com.depromeet.archive.domain.user.entity.UserRole;
import com.depromeet.archive.domain.user.info.UserInfo;
import com.depromeet.archive.security.oauth.UserPrincipalConverter;
import com.depromeet.archive.security.common.UserPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class KakaoPrincipalConverter implements UserPrincipalConverter {

    @Override
    public String getProviderId() {
        return "kakao";
    }

    @Override
    public UserPrincipal convert(OAuth2User oAuth2User) {
        Map<String, Object> attributes = ((Map) oAuth2User.getAttribute("kakao_account"));
        String email = (String) attributes.get("email");
        UserInfo info = UserInfo.builder()
                .mailAddress(email)
                .userRole(UserRole.GENERAL)
                .build();
        return UserPrincipal
                .builder()
                .userInfo(info)
                .attributes(attributes)
                .build();
    }
}

package com.depromeet.archive.domain.user.entity;

import com.depromeet.archive.domain.user.info.UserInfo;
import com.depromeet.archive.security.common.UserPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Arrays;
import java.util.Map;

public enum OAuthProvider {

    KAKAO("kakao") {
        @Override
        public UserPrincipal convert(OAuth2User oAuth2User) {
            Map<String, Object> attributes = oAuth2User.getAttribute("kakao_account");
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

    }, APPLE("apple") {
        @Override
        public UserPrincipal convert(OAuth2User user) {
            return null;
        }
    };

    private final String registrationId;

    OAuthProvider(String registrationId) {
        this.registrationId = registrationId;
    }


    public static OAuthProvider getByRegistrationId(String id) {
        return Arrays.stream(OAuthProvider.values())
                .filter((it)-> it.registrationId.equals(id))
                .findFirst()
                .orElseThrow(()->new IllegalStateException("Provider id does not exist: " + id));
    }


    public abstract UserPrincipal convert(OAuth2User user);
}

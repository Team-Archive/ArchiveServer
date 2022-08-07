package site.archive.domain.user;

import java.util.Arrays;

public enum OAuthProvider {

    KAKAO("kakao") {
        @Override
        public String getOAuth2UserInfoKey() {
            return "kakao_account";
        }
    },
    APPLE("apple") {
        @Override
        public String getOAuth2UserInfoKey() {
            return null;
        }
    };

    private final String registrationId;

    OAuthProvider(String registrationId) {
        this.registrationId = registrationId;
    }

    public static OAuthProvider getByRegistrationId(String id) {
        return Arrays.stream(OAuthProvider.values())
                     .filter(it -> it.registrationId.equals(id))
                     .findFirst()
                     .orElseThrow(() -> new IllegalStateException("Provider id does not exist: " + id));
    }

    public abstract String getOAuth2UserInfoKey();

    public String getRegistrationId() {
        return registrationId;
    }

}

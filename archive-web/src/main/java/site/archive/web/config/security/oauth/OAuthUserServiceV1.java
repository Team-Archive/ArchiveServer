package site.archive.web.config.security.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import site.archive.domain.user.OAuthProvider;
import site.archive.domain.user.UserInfo;
import site.archive.domain.user.UserRole;
import site.archive.dto.v1.auth.OAuthRegisterCommandV1;
import site.archive.service.user.UserRegisterServiceV1;
import site.archive.web.config.security.common.UserPrincipal;

import java.util.Map;
import java.util.Objects;


// TODO: 미사용, 추후 Android 지원을 위해 남겨둠
@Deprecated
public class OAuthUserServiceV1 extends DefaultOAuth2UserService {

    private final UserRegisterServiceV1 userRegisterServiceV1;

    public OAuthUserServiceV1(UserRegisterServiceV1 userRegisterServiceV1) {
        this.userRegisterServiceV1 = userRegisterServiceV1;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        var oAuth2User = super.loadUser(oAuth2UserRequest);
        var provider = getProvider(oAuth2UserRequest);
        var principal = convert(oAuth2User, provider);
        assert principal != null; // TODO: need refactoring
        registerOrUpdateUser(principal, provider);
        return principal;
    }

    private OAuthProvider getProvider(OAuth2UserRequest userRequest) {
        var registrationId = userRequest.getClientRegistration().getRegistrationId();
        return OAuthProvider.getByRegistrationId(registrationId);
    }

    private void registerOrUpdateUser(UserPrincipal principal, OAuthProvider provider) {
        var command = new OAuthRegisterCommandV1(principal.getName(), provider);
        var userId = userRegisterServiceV1.getOrRegisterUser(command);
        principal.setUserId(userId);
    }

    private UserPrincipal convert(OAuth2User oAuth2User, OAuthProvider oAuthProvider) {
        Map<String, Object> attributes = oAuth2User.getAttribute(oAuthProvider.getOAuth2UserInfoKey());

        var email = (String) Objects.requireNonNull(attributes).get("email");
        var info = UserInfo.builder()
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

package site.archive.web.config.security.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import site.archive.domain.user.OAuthProvider;
import site.archive.domain.user.UserInfo;
import site.archive.domain.user.UserRole;
import site.archive.dto.v1.auth.OAuthRegisterCommand;
import site.archive.service.user.UserRegisterService;
import site.archive.web.config.security.common.UserPrincipal;

import java.util.Map;
import java.util.Objects;

public class OAuthUserService extends DefaultOAuth2UserService {

    private final UserRegisterService userRegisterService;

    public OAuthUserService(UserRegisterService userRegisterService) {
        this.userRegisterService = userRegisterService;
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
        var command = new OAuthRegisterCommand(principal.getName(), provider);
        var userId = userRegisterService.getOrRegisterUser(command);
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

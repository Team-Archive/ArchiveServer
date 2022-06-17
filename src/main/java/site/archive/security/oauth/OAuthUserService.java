package site.archive.security.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import site.archive.api.command.OAuthRegisterCommand;
import site.archive.domain.user.UserRegisterService;
import site.archive.domain.user.entity.OAuthProvider;
import site.archive.security.common.UserPrincipal;

public class OAuthUserService extends DefaultOAuth2UserService {

    private final UserRegisterService userRegisterService;

    public OAuthUserService(UserRegisterService userRegisterService) {
        this.userRegisterService = userRegisterService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        var oAuth2User = super.loadUser(oAuth2UserRequest);
        var provider = getProvider(oAuth2UserRequest);
        var principal = provider.convert(oAuth2User);
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
}

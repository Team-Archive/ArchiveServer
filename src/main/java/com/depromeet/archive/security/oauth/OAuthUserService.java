package com.depromeet.archive.security.oauth;

import com.depromeet.archive.common.exception.ResourceNotFoundException;
import com.depromeet.archive.domain.user.UserService;
import com.depromeet.archive.domain.user.command.BasicRegisterCommand;
import com.depromeet.archive.security.common.UserPrincipal;
import com.depromeet.archive.security.exception.WrappingAuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.Map;

public class OAuthUserService extends DefaultOAuth2UserService {

    private final Map<String, UserPrincipalConverter> factoryMap = new HashMap<>();
    private final UserService userService;

    public OAuthUserService(UserService service) {
        this.userService = service;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(oAuth2UserRequest);
        UserPrincipalConverter converter = getConverter(oAuth2UserRequest);
        UserPrincipal principal = converter.convert(user);
        assert principal != null;
        registerOrUpdateUser(principal);
        return principal;
    }

    private UserPrincipalConverter getConverter(OAuth2UserRequest userRequest) {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        if (!factoryMap.containsKey(registrationId))
            throw new WrappingAuthenticationException(new ResourceNotFoundException("No provider found on oauth: " + registrationId));
        return factoryMap.get(registrationId);
    }

    private void registerOrUpdateUser(UserPrincipal principal) {
        BasicRegisterCommand registerCommand = new BasicRegisterCommand(principal.getName());
        long userId = userService.updateNonCredentialUser(registerCommand);
        principal.setUserId(userId);
    }

    public void addPrincipalConverter(UserPrincipalConverter converter) {
        factoryMap.put(converter.getProviderId(), converter);
    }

}

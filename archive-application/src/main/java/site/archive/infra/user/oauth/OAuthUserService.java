package site.archive.infra.user.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.stereotype.Service;
import site.archive.dto.v1.auth.OAuthRegisterCommandV1;
import site.archive.dto.v1.user.OAuthRegisterRequestDtoV1;
import site.archive.dto.v2.OAuthLoginRequestDto;
import site.archive.dto.v2.OAuthRegisterRequestDto;
import site.archive.dto.v2.OAuthUserInfoRequestDto;
import site.archive.infra.user.oauth.provider.OAuthProviderClient;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuthUserService {

    private final List<OAuthProviderClient> oAuthProviderClients;

    public OAuthRegisterCommandV1 getOAuthRegisterInfo(OAuthRegisterRequestDtoV1 request) {
        log.debug("oauth provider access token: {}", request);
        var provider = request.getProvider();
        var oAuthProviderClient = getOAuthProviderClient(provider);
        return oAuthProviderClient.getOAuthRegisterInfo(request.getToken());
    }

    public OAuthRegisterRequestDto getOAuthRegisterInfo(OAuthUserInfoRequestDto request) {
        log.debug("oauth provider access token: {}", request);
        var provider = request.getProvider();
        var oAuthProviderClient = getOAuthProviderClient(provider);
        var email = oAuthProviderClient.getEmail(request.getToken());
        return new OAuthRegisterRequestDto(oAuthProviderClient.getProvider(), email, request.getNickname());
    }

    public String getOAuthEmail(OAuthLoginRequestDto request) {
        log.debug("oauth provider access token: {}", request);
        var provider = request.getProvider();
        var oAuthProviderClient = getOAuthProviderClient(provider);
        return oAuthProviderClient.getEmail(request.getToken());
    }

    private OAuthProviderClient getOAuthProviderClient(String provider) {
        return oAuthProviderClients.stream()
                                   .filter(client -> client.getProvider().getRegistrationId().equals(provider))
                                   .findFirst()
                                   .orElseThrow(() -> new ProviderNotFoundException(
                                       "There is no suitable register provider client for " + provider));
    }

}

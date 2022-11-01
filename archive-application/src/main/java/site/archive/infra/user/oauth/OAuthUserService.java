package site.archive.infra.user.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.stereotype.Service;
import site.archive.dto.v1.auth.OAuthRegisterCommand;
import site.archive.dto.v1.user.OAuthRegisterRequestDto;
import site.archive.infra.user.oauth.provider.OAuthProviderClient;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuthUserService {

    private final List<OAuthProviderClient> oAuthProviderClients;

    public OAuthRegisterCommand getOAuthRegisterInfo(OAuthRegisterRequestDto oAuthRegisterRequestDto) {
        var provider = oAuthRegisterRequestDto.getProvider();
        var oAuthProviderClient = oAuthProviderClients.stream()
                                                      .filter(client -> client.support().equals(provider))
                                                      .findFirst()
                                                      .orElseThrow(() ->
                                                                       new ProviderNotFoundException(
                                                                           "There is no suitable register provider client for " + provider));
        log.debug("oauth provider access token: {}", oAuthRegisterRequestDto);
        return oAuthProviderClient.getOAuthRegisterInfo(oAuthRegisterRequestDto);
    }

}

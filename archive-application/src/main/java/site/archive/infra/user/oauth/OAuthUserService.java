package site.archive.infra.user.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.stereotype.Service;
import site.archive.dto.v1.auth.OAuthRegisterCommandV1;
import site.archive.dto.v1.user.OAuthRegisterRequestDto;
import site.archive.infra.user.oauth.provider.OAuthProviderClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OAuthUserService {

    private final List<OAuthProviderClient> oAuthProviderClients;

    public OAuthRegisterCommandV1 getOAuthRegisterInfo(OAuthRegisterRequestDto oAuthRegisterRequestDto) {
        var provider = oAuthRegisterRequestDto.getProvider();
        var oAuthProviderClient = oAuthProviderClients.stream()
                                                      .filter(client -> client.support().equals(provider))
                                                      .findFirst()
                                                      .orElseThrow(() ->
                                                                       new ProviderNotFoundException(
                                                                           "There is no suitable register provider client for " + provider));
        return oAuthProviderClient.getOAuthRegisterInfo(oAuthRegisterRequestDto);
    }

}

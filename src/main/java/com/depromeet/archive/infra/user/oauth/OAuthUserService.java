package com.depromeet.archive.infra.user.oauth;

import com.depromeet.archive.api.dto.user.OAuthRegisterDto;
import com.depromeet.archive.domain.user.command.OAuthRegisterCommand;
import com.depromeet.archive.infra.user.oauth.provider.OAuthProviderClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OAuthUserService {

    private final List<OAuthProviderClient> oAuthProviderClients;

    public OAuthRegisterCommand getOAuthRegisterInfo(OAuthRegisterDto oAuthRegisterDto) {
        var provider = oAuthRegisterDto.getProvider();
        var oAuthProviderClient = oAuthProviderClients.stream()
                                                      .filter(client -> client.support().equals(provider))
                                                      .findFirst()
                                                      .orElseThrow(() ->
                                                                       new ProviderNotFoundException(
                                                                           "There is no suitable register provider client for " + provider));
        return oAuthProviderClient.getOAuthRegisterInfo(oAuthRegisterDto);
    }

}

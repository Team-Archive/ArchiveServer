package com.depromeet.archive.infra.user.provider;

import com.depromeet.archive.api.dto.user.OAuthRegisterDto;
import com.depromeet.archive.domain.user.command.OAuthRegisterCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OAuthProviderService {

    private final List<OAuthProviderClient> oAuthProviderClients;

    public OAuthRegisterCommand getOAuthRegisterInfo(String provider, OAuthRegisterDto oAuthRegisterDto) {
        var oAuthProviderClient = oAuthProviderClients.stream()
                .filter(client -> client.support().equals(provider))
                .findFirst()
                .orElseThrow(() ->
                        new ProviderNotFoundException("There is no suitable register provider client for " + provider));
        return oAuthProviderClient.getOAuthRegisterInfo(oAuthRegisterDto);
    }

}

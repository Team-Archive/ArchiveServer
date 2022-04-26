package com.depromeet.archive.infra.user.oauth.provider;

import com.depromeet.archive.api.dto.user.OAuthRegisterDto;
import com.depromeet.archive.domain.user.command.OAuthRegisterCommand;
import com.depromeet.archive.domain.user.entity.OAuthProvider;
import com.depromeet.archive.exception.user.OAuthRegisterFailException;
import com.depromeet.archive.infra.user.oauth.provider.dto.AppleTokenPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
@RequiredArgsConstructor
public class AppleClient implements OAuthProviderClient {

    private final ObjectMapper objectMapper;

    @Override
    public String support() {
        return OAuthProvider.APPLE.getRegistrationId();
    }

    @Override
    public OAuthRegisterCommand getOAuthRegisterInfo(OAuthRegisterDto oAuthRegisterDto) {
        try {
            var jwtClaimsSet = SignedJWT.parse(oAuthRegisterDto.getToken()).getJWTClaimsSet();
            var payload = objectMapper.convertValue(jwtClaimsSet.toJSONObject(), AppleTokenPayload.class);
            return new OAuthRegisterCommand(payload.getEmail(), OAuthProvider.APPLE);
        } catch (ParseException e) {
            throw new OAuthRegisterFailException(OAuthProvider.APPLE, "Apple token parse failure.");
        }
    }

}

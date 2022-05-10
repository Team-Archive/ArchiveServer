package com.depromeet.archive.infra.user.oauth.provider;

import com.depromeet.archive.api.dto.user.OAuthRegisterDto;
import com.depromeet.archive.domain.user.command.OAuthRegisterCommand;
import com.depromeet.archive.domain.user.entity.OAuthProvider;
import com.depromeet.archive.exception.user.OAuthRegisterFailException;
import com.depromeet.archive.infra.user.oauth.provider.dto.ApplePublicKeys;
import com.depromeet.archive.infra.user.oauth.provider.dto.AppleTokenPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.ZoneOffset.UTC;

@Component
@EnableConfigurationProperties(AppleClient.AppleOAuthProperty.class)
@RequiredArgsConstructor
@Slf4j
public class AppleClient implements OAuthProviderClient {

    private final AppleOAuthProperty appleOAuthProperty;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Override
    public String support() {
        return OAuthProvider.APPLE.getRegistrationId();
    }

    @Override
    public OAuthRegisterCommand getOAuthRegisterInfo(OAuthRegisterDto oAuthRegisterDto) {
        var jwtToken = getSignedJWT(oAuthRegisterDto.getToken());
        var payload = getAppleTokenPayload(jwtToken);
        AppleTokenVerifier.verify(objectMapper, restTemplate, appleOAuthProperty, jwtToken, payload);
        return new OAuthRegisterCommand(payload.getEmail(), OAuthProvider.APPLE);
    }

    private SignedJWT getSignedJWT(final String jwtToken) {
        try {
            return SignedJWT.parse(jwtToken);
        } catch (ParseException e) {
            throw new OAuthRegisterFailException(OAuthProvider.APPLE, "Apple token parse failure. " + e.getMessage());
        }
    }

    private AppleTokenPayload getAppleTokenPayload(final SignedJWT jwtToken) {
        try {
            var jwtClaimsSet = jwtToken.getJWTClaimsSet();
            return objectMapper.convertValue(jwtClaimsSet.toJSONObject(), AppleTokenPayload.class);
        } catch (ParseException e) {
            throw new OAuthRegisterFailException(OAuthProvider.APPLE, "Apple token parse failure. " + e.getMessage());
        }
    }

    public static class AppleTokenVerifier {

        private AppleTokenVerifier() {
        }

        public static void verify(final ObjectMapper objectMapper,
                                  final RestTemplate restTemplate,
                                  final AppleOAuthProperty appleOAuthProperty,
                                  final SignedJWT jwtToken,
                                  final AppleTokenPayload payload) {
            verifyExpirationDate(payload.getExp());
            verifyAudience(appleOAuthProperty.getAudience(), payload.getAud());
            verifyIssuer(appleOAuthProperty.getIssuer(), payload.getIss());
            verifyJwtSignedKeyIsApplePublicKey(objectMapper, restTemplate,
                                               appleOAuthProperty.getPublicKeyUri(), jwtToken);
            log.debug("Success apple token verify: payload({})", payload);
        }

        private static void verifyExpirationDate(long expirationDateOfSecs) {
            var expireDateTime = Instant.ofEpochSecond(expirationDateOfSecs).atZone(UTC).toLocalDateTime();
            if (!LocalDateTime.now(UTC).isBefore(expireDateTime)) {
                throw new OAuthRegisterFailException(OAuthProvider.APPLE, "Apple token is expired.");
            }
        }

        private static void verifyAudience(String audience, String audienceByToken) {
            if (!audience.equals(audienceByToken)) {
                throw new OAuthRegisterFailException(OAuthProvider.APPLE, "Audience of Apple token is wrong.");
            }
        }

        private static void verifyIssuer(String issuer, String issuerByToken) {
            if (!issuer.equals(issuerByToken)) {
                throw new OAuthRegisterFailException(OAuthProvider.APPLE, "Issuer of Apple token is wrong.");
            }
        }

        private static void verifyJwtSignedKeyIsApplePublicKey(ObjectMapper objectMapper,
                                                               RestTemplate restTemplate,
                                                               String applePublicKeyUri,
                                                               SignedJWT jwtToken) {
            var verifiers = getPublicKeyVerifiers(restTemplate, objectMapper, applePublicKeyUri);
            if (!isJwtVerifiedByKeys(jwtToken, verifiers)) {
                throw new OAuthRegisterFailException(OAuthProvider.APPLE,
                                                     "This token isn't signed with the apple public key.");
            }

        }

        private static List<RSASSAVerifier> getPublicKeyVerifiers(RestTemplate restTemplate,
                                                                  ObjectMapper objectMapper,
                                                                  String applePublicKeyUri) {
            var response = restTemplate.getForEntity(applePublicKeyUri, ApplePublicKeys.class);
            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new OAuthRegisterFailException(OAuthProvider.APPLE, "Failed to get apple public key.");
            }
            return response.getBody().toVerifier(objectMapper);
        }

        private static boolean isJwtVerifiedByKeys(SignedJWT jwtToken, List<RSASSAVerifier> verifiers) {
            return verifiers.stream()
                            .map(verifier -> {
                                try {
                                    return jwtToken.verify(verifier);
                                } catch (JOSEException ex) {
                                    throw new OAuthRegisterFailException(OAuthProvider.APPLE,
                                                                         "Error occurred when create verifier using public key : " + ex.getMessage());
                                }
                            })
                            .filter(result -> result)
                            .findFirst()
                            .orElse(false);
        }

    }

    @ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.apple")
    @ConstructorBinding
    @AllArgsConstructor
    @Getter
    public static class AppleOAuthProperty {

        private String issuer;
        private String audience;
        private String publicKeyUri;

    }

}

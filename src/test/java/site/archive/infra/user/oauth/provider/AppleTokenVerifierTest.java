package site.archive.infra.user.oauth.provider;

import site.archive.exception.user.OAuthRegisterFailException;
import site.archive.infra.user.oauth.provider.AppleClient.AppleOAuthProperty;
import site.archive.infra.user.oauth.provider.AppleClient.AppleTokenVerifier;
import site.archive.infra.user.oauth.provider.dto.ApplePublicKeys;
import site.archive.infra.user.oauth.provider.dto.AppleTokenPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AppleTokenVerifierTest {

    private static final String issuer = "Iss";
    private static final String audience = "Aud";
    private static final String publicKeyUri = "Public-Key-Uri";

    private ObjectMapper objectMapper;
    private AppleOAuthProperty appleOAuthProperty;

    @Mock
    SignedJWT jwtMock;
    @Mock
    RestTemplate restTemplate;
    @Mock
    ApplePublicKeys applePublicKeys;
    @Mock
    RSASSAVerifier verifier;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        appleOAuthProperty = new AppleOAuthProperty(issuer, audience, publicKeyUri);
    }

    @Test
    void 올바른_토큰의_경우_검증에_성공한다() throws JOSEException, JsonProcessingException {
        // given
        var applePublicKeyResponse = new ResponseEntity<Object>(applePublicKeys, HttpStatus.OK);
        given(restTemplate.getForEntity(anyString(), any())).willReturn(applePublicKeyResponse);
        given(jwtMock.verify(any())).willReturn(true);
        given(applePublicKeys.toVerifier(any())).willReturn(List.of(verifier));

        // and set correct payload
        var expirationDateOfSecs = LocalDateTime.now(UTC).plusDays(1L).toEpochSecond(UTC);
        var payload = String.format("{\"iss\":\"%s\", \"aud\":\"%s\", \"exp\":%d}",
                                    issuer, audience, expirationDateOfSecs);
        var appleTokenPayload = objectMapper.readValue(payload, AppleTokenPayload.class);

        // when & then
        assertDoesNotThrow(() -> AppleTokenVerifier.verify(objectMapper,
                                                           restTemplate,
                                                           appleOAuthProperty,
                                                           jwtMock,
                                                           appleTokenPayload));
    }

    @Test
    void 만료된_토큰의_경우_검증에_실패한다() throws JsonProcessingException {
        // given
        var expiredDateOfSecs = LocalDateTime.now(UTC).minusDays(1L).toEpochSecond(UTC);
        var payload = String.format("{\"iss\":\"%s\", \"aud\":\"%s\", \"exp\":%d}",
                                    issuer, audience, expiredDateOfSecs);
        var appleTokenPayload = objectMapper.readValue(payload, AppleTokenPayload.class);

        // when & then
        var exception = assertThrows(OAuthRegisterFailException.class,
                                     () -> AppleTokenVerifier.verify(objectMapper,
                                                                     restTemplate,
                                                                     appleOAuthProperty,
                                                                     jwtMock,
                                                                     appleTokenPayload));
        assertThat(exception.getMessage()).contains("token is expired");
    }

    @Test
    void 잘못된_APPLE_CLIENT_ID_aud_토큰의_경우_검증에_실패한다() throws JsonProcessingException {
        // given
        var expiredDateOfSecs = LocalDateTime.now(UTC).plusDays(1L).toEpochSecond(UTC);
        var incorrectAppleClientId = "incorrect_apple_client_id_value(aud)";
        var payload = String.format("{\"iss\":\"%s\", \"aud\":\"%s\", \"exp\":%d}",
                                    issuer, incorrectAppleClientId, expiredDateOfSecs);
        var appleTokenPayload = objectMapper.readValue(payload, AppleTokenPayload.class);

        // when & then
        var exception = assertThrows(OAuthRegisterFailException.class,
                                     () -> AppleTokenVerifier.verify(objectMapper,
                                                                     restTemplate,
                                                                     appleOAuthProperty,
                                                                     jwtMock,
                                                                     appleTokenPayload));
        assertThat(exception.getMessage()).contains("Audience of Apple token is wrong");
    }

    @Test
    void Apple_public_key로_암호화_되지_않은_jwt의_경우_검증에_실패한다() throws JsonProcessingException, JOSEException {
        // given
        var applePublicKeyResponse = new ResponseEntity<Object>(applePublicKeys, HttpStatus.OK);
        given(restTemplate.getForEntity(anyString(), any())).willReturn(applePublicKeyResponse);
        given(jwtMock.verify(any())).willReturn(false);
        given(applePublicKeys.toVerifier(any())).willReturn(List.of(verifier));

        // and set correct payload
        var expirationDateOfSecs = LocalDateTime.now(UTC).plusDays(1L).toEpochSecond(UTC);
        var payload = String.format("{\"iss\":\"%s\", \"aud\":\"%s\", \"exp\":%d}",
                                    issuer, audience, expirationDateOfSecs);
        var appleTokenPayload = objectMapper.readValue(payload, AppleTokenPayload.class);

        // when & then
        var exception = assertThrows(OAuthRegisterFailException.class,
                                     () -> AppleTokenVerifier.verify(objectMapper,
                                                                     restTemplate,
                                                                     appleOAuthProperty,
                                                                     jwtMock,
                                                                     appleTokenPayload));
        assertThat(exception.getMessage()).contains("This token isn't signed with the apple public key");
    }

}
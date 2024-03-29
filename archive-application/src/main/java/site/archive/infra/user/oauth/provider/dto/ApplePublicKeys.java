package site.archive.infra.user.oauth.provider.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.Getter;
import site.archive.common.exception.user.OAuthRegisterFailException;
import site.archive.domain.user.OAuthProvider;

import java.text.ParseException;
import java.util.List;

@Getter
public class ApplePublicKeys {

    private List<Key> keys;

    public List<RSASSAVerifier> toVerifier(ObjectMapper objectMapper) {
        return keys.stream()
                   .map(key -> key.toRSAVerifier(objectMapper))
                   .toList();
    }

    @Getter
    public static class Key {
        private String kty;
        private String kid;
        private String use;
        private String alg;
        private String n;
        private String e;

        public RSASSAVerifier toRSAVerifier(ObjectMapper objectMapper) {
            try {
                var rsaKey = (RSAKey) JWK.parse(objectMapper.writeValueAsString(this));
                var publicKey = rsaKey.toRSAPublicKey();
                return new RSASSAVerifier(publicKey);
            } catch (ParseException | JsonProcessingException | JOSEException ex) {
                throw new OAuthRegisterFailException(OAuthProvider.APPLE.getRegistrationId(),
                                                     "Error occurred when create verifier using public key : " + ex.getMessage());
            }
        }
    }

}
package site.archive.infra.user.oauth.provider.dto;

import site.archive.domain.user.entity.OAuthProvider;
import site.archive.exception.user.OAuthRegisterFailException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.Getter;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ApplePublicKeys {

    private List<Key> keys;

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
                throw new OAuthRegisterFailException(OAuthProvider.APPLE,
                                                     "Error occurred when create verifier using public key : " + ex.getMessage());
            }
        }
    }

    public List<RSASSAVerifier> toVerifier(ObjectMapper objectMapper) {
        return keys.stream()
            .map(key -> key.toRSAVerifier(objectMapper))
            .collect(Collectors.toList());
    }

}
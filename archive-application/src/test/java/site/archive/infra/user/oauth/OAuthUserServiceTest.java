package site.archive.infra.user.oauth;

import org.junit.Test;
import org.springframework.security.authentication.ProviderNotFoundException;
import site.archive.domain.user.OAuthProvider;
import site.archive.dto.v1.user.OAuthRegisterRequestDtoV1;
import site.archive.infra.user.oauth.provider.AppleClient;
import site.archive.infra.user.oauth.provider.KakaoClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OAuthUserServiceTest {

    @Test
    void OAuth_Provider_Client_구현체가_없으면_오류를_반환한다() {
        // given
        var providerClients = List.of(new KakaoClient(null),
                                      new AppleClient(null, null, null));
        var oAuthUserService = new OAuthUserService(providerClients);

        var oAuthRegisterDto = new OAuthRegisterRequestDtoV1("naver", "token");
        assertThatThrownBy(() -> oAuthUserService.getOAuthRegisterInfo(oAuthRegisterDto))
            .isInstanceOf(ProviderNotFoundException.class);
    }

    @Test
    void provider에_맞는_OAuth_Provider_Client_구현체의_메서드를_호출한다() {
        // given
        var appleClient = mock(AppleClient.class);
        var providerClients = List.of(new KakaoClient(null), appleClient);
        var oAuthUserService = new OAuthUserService(providerClients);

        // mocking
        when(appleClient.getProvider()).thenReturn(OAuthProvider.APPLE);
        when(appleClient.getOAuthRegisterInfo(any())).thenReturn(null);

        // when & then
        var oAuthRegisterDto = new OAuthRegisterRequestDtoV1("apple", "token");
        assertDoesNotThrow(() -> oAuthUserService.getOAuthRegisterInfo(oAuthRegisterDto));
        verify(appleClient).getOAuthRegisterInfo(any());
    }

}
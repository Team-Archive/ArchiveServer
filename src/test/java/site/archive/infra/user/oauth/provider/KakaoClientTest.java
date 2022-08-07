package site.archive.infra.user.oauth.provider;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import site.archive.api.v1.dto.user.OAuthRegisterRequestDto;
import site.archive.common.exception.user.OAuthRegisterFailException;
import site.archive.domain.user.entity.OAuthProvider;
import site.archive.infra.user.oauth.provider.dto.KakaoUserInfo;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class KakaoClientTest {

    @Mock
    RestTemplate restTemplate;

    @Test
    void 정상적인_요청에_따라_KAKAO_이메일을_반환한다() {
        // given
        var kakaoEmail = "sample@test.com";
        var kakaoAccount = new KakaoUserInfo.KakaoAccount(kakaoEmail, Collections.emptyMap());
        var kakaoUserInfo = new KakaoUserInfo("id", Collections.emptyMap(), kakaoAccount);
        var kakaoUserInfoResponse = new ResponseEntity<>(kakaoUserInfo, HttpStatus.OK);

        var kakaoClient = new KakaoClient(restTemplate);
        var oAuthRegisterDto = new OAuthRegisterRequestDto(kakaoClient.support(), "token");

        given(restTemplate.exchange(any(), any(), any(), eq(KakaoUserInfo.class), any(Object.class)))
            .willReturn(kakaoUserInfoResponse);

        // when
        var kakaoRegisterCommand = kakaoClient.getOAuthRegisterInfo(oAuthRegisterDto);

        // then
        assertThat(kakaoRegisterCommand.getProvider()).isEqualTo(OAuthProvider.KAKAO);
        assertThat(kakaoRegisterCommand.getEmail()).isEqualTo(kakaoEmail);
    }

    @Test
    void KAKAO의_유저_정보_반환값이_올바르지_않은_경우_OAuthRegisterFailException가_발생한다() {
        // given
        var kakaoUserInfo = new KakaoUserInfo("id", Collections.emptyMap(), null);
        var kakaoUserInfoUnauthorizedResponse = new ResponseEntity<>(kakaoUserInfo, HttpStatus.UNAUTHORIZED);

        var kakaoClient = new KakaoClient(restTemplate);
        var oAuthRegisterDto = new OAuthRegisterRequestDto(kakaoClient.support(), "token");

        given(restTemplate.exchange(any(), any(), any(), eq(KakaoUserInfo.class), any(Object.class)))
            .willReturn(kakaoUserInfoUnauthorizedResponse);

        // when & then
        var exception = assertThrows(OAuthRegisterFailException.class,
                                     () -> kakaoClient.getOAuthRegisterInfo(oAuthRegisterDto));
        assertThat(exception.getMessage()).contains(OAuthProvider.KAKAO.getRegistrationId(),
                                                    "UserInfoUrl Response error");
    }

}
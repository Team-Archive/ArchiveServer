package site.archive.web.api.docs.swagger;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import site.archive.domain.user.UserInfo;
import site.archive.dto.v1.user.UserPasswordResetRequestDtoV1;
import site.archive.dto.v2.OAuthLoginRequestDto;
import site.archive.dto.v2.OAuthUserInfoRequestDto;
import site.archive.dto.v2.PasswordRegisterRequestDto;

public interface UserAuthControllerV2Docs {

    @Operation(summary = "비밀번호 초기화 - 새로운 비밀번호 설정")
    ResponseEntity<Void> resetPassword(UserInfo userInfo,
                                       UserPasswordResetRequestDtoV1 userPasswordResetRequestDtoV1);

    @Operation(summary = "[NoAuth] 패스워드 유저 회원가입")
    ResponseEntity<Void> registerPasswordUser(PasswordRegisterRequestDto passwordRegisterRequest);

    @Operation(summary = "[NoAuth] 소셜 유저 회원가입")
    ResponseEntity<Void> registerSocialUser(OAuthUserInfoRequestDto oAuthUserInfoRequest);

    @Operation(summary = "[NoAuth] 소셜 유저 로그인")
    ResponseEntity<Void> loginSocialUser(OAuthLoginRequestDto oAuthLoginRequestDto);

}

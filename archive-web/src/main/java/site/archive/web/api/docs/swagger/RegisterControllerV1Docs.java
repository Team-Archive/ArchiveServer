package site.archive.web.api.docs.swagger;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import site.archive.dto.v1.auth.PasswordRegisterCommandV1;
import site.archive.dto.v1.user.OAuthRegisterRequestDtoV1;

public interface RegisterControllerV1Docs {

    @Operation(summary = "[Deprecated -> /api/v2/auth/register] 패스워드 유저 회원가입")
    ResponseEntity<Void> registerUser(PasswordRegisterCommandV1 command);

    @Operation(summary = "[Deprecated -> /api/v2/auth/register/social, login/social] 소셜 로그인 유저 회원가입 및 로그인")
    ResponseEntity<Void> registerOrLoginSocialUser(OAuthRegisterRequestDtoV1 oAuthRegisterRequestDtoV1);

}

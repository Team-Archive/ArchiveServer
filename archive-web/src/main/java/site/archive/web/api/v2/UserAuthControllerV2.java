package site.archive.web.api.v2;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.archive.dto.v1.user.UserPasswordResetRequestDtoV1;
import site.archive.dto.v2.PasswordRegisterRequestDto;
import site.archive.infra.user.oauth.OAuthUserService;
import site.archive.service.user.UserAuthService;
import site.archive.service.user.UserRegisterServiceV2;
import site.archive.web.config.security.token.jwt.JwtAuthenticationToken;

@RestController
@RequestMapping("/api/v2/auth")
@RequiredArgsConstructor
public class UserAuthControllerV2 {

    private final UserAuthService userAuthService;
    private final UserRegisterServiceV2 userRegisterService;
    private final OAuthUserService oAuthUserService;
    private final PasswordEncoder encoder;

    @Operation(summary = "비밀번호 초기화 - 새로운 비밀번호 설정")
    @PostMapping("/password/reset")
    public ResponseEntity<Void> resetPassword(@Validated @RequestBody UserPasswordResetRequestDtoV1 userPasswordResetRequestDtoV1) {
        userAuthService.resetPassword(userPasswordResetRequestDtoV1);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "[NoAuth] 패스워드 유저 회원가입")
    @PostMapping("/register")
    public ResponseEntity<Void> registerPasswordUser(@Validated @RequestBody PasswordRegisterRequestDto passwordRegisterRequest) {
        passwordRegisterRequest.updatePasswordToEncrypt(encoder.encode(passwordRegisterRequest.getPassword()));
        var userInfo = userRegisterService.registerUser(passwordRegisterRequest).convertToUserInfo();
        SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(userInfo));
        return ResponseEntity.ok().build();
    }

}

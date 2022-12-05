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
import site.archive.domain.user.UserInfo;
import site.archive.dto.v1.user.UserPasswordResetRequestDtoV1;
import site.archive.dto.v2.OAuthLoginRequestDto;
import site.archive.dto.v2.OAuthUserInfoRequestDto;
import site.archive.dto.v2.PasswordRegisterRequestDto;
import site.archive.infra.user.oauth.OAuthUserService;
import site.archive.service.user.UserAuthService;
import site.archive.service.user.UserRegisterServiceV2;
import site.archive.service.user.UserService;
import site.archive.web.api.resolver.annotation.RequestUser;
import site.archive.web.config.security.token.jwt.JwtAuthenticationToken;

@RestController
@RequestMapping("/api/v2/auth")
@RequiredArgsConstructor
public class UserAuthControllerV2 {

    private final UserService userService;
    private final UserAuthService userAuthService;
    private final UserRegisterServiceV2 userRegisterService;
    private final OAuthUserService oAuthUserService;
    private final PasswordEncoder encoder;

    @Operation(summary = "비밀번호 초기화 - 새로운 비밀번호 설정")
    @PostMapping("/password/reset")
    public ResponseEntity<Void> resetPassword(@RequestUser UserInfo userInfo,
                                              @Validated @RequestBody UserPasswordResetRequestDtoV1 userPasswordResetRequestDtoV1) {
        userAuthService.resetPassword(userInfo, userPasswordResetRequestDtoV1);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "[NoAuth] 패스워드 유저 회원가입")
    @PostMapping("/register")
    public ResponseEntity<Void> registerPasswordUser(@Validated @RequestBody PasswordRegisterRequestDto passwordRegisterRequest) {
        passwordRegisterRequest.updatePasswordToEncrypt(encoder.encode(passwordRegisterRequest.getPassword()));
        var userInfo = userRegisterService.registerUser(passwordRegisterRequest);
        SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(userInfo));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "[NoAuth] 소셜 유저 회원가입")
    @PostMapping("/register/social")
    public ResponseEntity<Void> registerSocialUser(@Validated @RequestBody OAuthUserInfoRequestDto oAuthUserInfoRequest) {
        var oAuthRegisterRequest = oAuthUserService.getOAuthRegisterInfo(oAuthUserInfoRequest);
        var userInfo = userRegisterService.registerUser(oAuthRegisterRequest);
        SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(userInfo));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "[NoAuth] 소셜 유저 로그인")
    @PostMapping("/login/social")
    public ResponseEntity<Void> loginSocialUser(@Validated @RequestBody OAuthLoginRequestDto oAuthLoginRequestDto) {
        var oAuthEmail = oAuthUserService.getOAuthEmail(oAuthLoginRequestDto);
        var user = userService.findUserByEmail(oAuthEmail);
        var userInfo = new UserInfo(user.getMailAddress(), user.getUserRole(), user.getUserId());
        SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(userInfo));
        return ResponseEntity.ok().build();
    }

}

package site.archive.web.api.v1;

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
import site.archive.dto.v1.auth.PasswordRegisterCommand;
import site.archive.dto.v1.user.OAuthRegisterRequestDto;
import site.archive.infra.user.oauth.OAuthUserService;
import site.archive.service.user.UserRegisterService;
import site.archive.web.config.security.token.jwt.JwtAuthenticationToken;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RegisterControllerV1 {

    private final UserRegisterService userRegisterService;
    private final OAuthUserService oAuthUserService;
    private final PasswordEncoder encoder;

    @Operation(summary = "[NoAuth] 패스워드 유저 회원가입")
    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@Validated @RequestBody PasswordRegisterCommand command) {
        encryptPassword(command);
        var userInfo = userRegisterService.registerUser(command).convertToUserInfo();
        SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(userInfo));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "[NoAuth] 소셜 로그인 유저 회원가입 및 로그인")
    @PostMapping("/social")
    public ResponseEntity<Void> registerOrLoginSocialUser(@Validated @RequestBody OAuthRegisterRequestDto oAuthRegisterRequestDto) {
        var oAuthRegisterInfo = oAuthUserService.getOAuthRegisterInfo(oAuthRegisterRequestDto);
        var userInfo = userRegisterService.getOrRegisterUserReturnInfo(oAuthRegisterInfo);
        SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(userInfo));
        return ResponseEntity.ok().build();
    }

    private void encryptPassword(PasswordRegisterCommand command) {
        command.setPassword(encoder.encode(command.getPassword()));
    }

}

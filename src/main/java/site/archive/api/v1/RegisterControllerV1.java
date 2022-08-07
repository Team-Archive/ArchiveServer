package site.archive.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.archive.api.command.PasswordRegisterCommand;
import site.archive.api.v1.dto.user.OAuthRegisterRequestDto;
import site.archive.domain.user.UserRegisterService;
import site.archive.domain.user.info.UserInfo;
import site.archive.infra.user.oauth.OAuthUserService;
import site.archive.config.security.token.HttpAuthTokenSupport;
import site.archive.config.security.token.TokenProvider;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RegisterControllerV1 {

    private final UserRegisterService userRegisterService;
    private final OAuthUserService oAuthUserService;
    private final PasswordEncoder encoder;

    // JWT token provider
    private final TokenProvider tokenProvider;
    private final HttpAuthTokenSupport tokenSupport;

    @Operation(summary = "[NoAuth] 패스워드 유저 회원가입")
    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid PasswordRegisterCommand command) {
        encryptPassword(command);
        userRegisterService.registerUser(command);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "[NoAuth] 소셜 로그인 유저 회원가입 및 로그인")
    @PostMapping("/social")
    public ResponseEntity<Void> registerOrLoginSocialUser(HttpServletResponse httpServletResponse,
                                                          @Validated @RequestBody OAuthRegisterRequestDto oAuthRegisterRequestDto) {
        var oAuthRegisterInfo = oAuthUserService.getOAuthRegisterInfo(oAuthRegisterRequestDto);
        var userInfo = userRegisterService.getOrRegisterUserReturnInfo(oAuthRegisterInfo);
        injectJwtToken(httpServletResponse, userInfo);
        return ResponseEntity.ok().build();

    }

    private void injectJwtToken(HttpServletResponse httpServletResponse, UserInfo userInfo) {
        var successToken = tokenProvider.createToken(userInfo);
        tokenSupport.injectToken(httpServletResponse, successToken);
        httpServletResponse.setStatus(HttpStatus.OK.value());
    }

    private void encryptPassword(PasswordRegisterCommand command) {
        command.setPassword(encoder.encode(command.getPassword()));
    }


}

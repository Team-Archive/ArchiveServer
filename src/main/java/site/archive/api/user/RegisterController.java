package site.archive.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.archive.api.dto.user.OAuthRegisterDto;
import site.archive.domain.user.UserRegisterService;
import site.archive.domain.user.command.PasswordRegisterCommand;
import site.archive.domain.user.info.UserInfo;
import site.archive.infra.user.oauth.OAuthUserService;
import site.archive.security.token.HttpAuthTokenSupport;
import site.archive.security.token.TokenProvider;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RegisterController {

    private final UserRegisterService userRegisterService;
    private final OAuthUserService oAuthUserService;
    private final PasswordEncoder encoder;

    // JWT token provider
    private final TokenProvider tokenProvider;
    private final HttpAuthTokenSupport tokenSupport;

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid PasswordRegisterCommand command) {
        encryptPassword(command);
        userRegisterService.registerUser(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/social")
    public ResponseEntity<Void> registerOrLoginSocialUser(HttpServletResponse httpServletResponse,
                                                          @Validated @RequestBody OAuthRegisterDto oAuthRegisterDto) {
        var oAuthRegisterInfo = oAuthUserService.getOAuthRegisterInfo(oAuthRegisterDto);
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

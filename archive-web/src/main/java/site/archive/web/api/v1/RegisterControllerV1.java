package site.archive.web.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.archive.dto.v1.auth.PasswordRegisterCommandV1;
import site.archive.dto.v1.user.OAuthRegisterRequestDtoV1;
import site.archive.infra.user.oauth.OAuthUserService;
import site.archive.service.user.UserRegisterServiceV1;
import site.archive.web.api.docs.swagger.RegisterControllerV1Docs;
import site.archive.web.config.security.token.jwt.JwtAuthenticationToken;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RegisterControllerV1 implements RegisterControllerV1Docs {

    private final UserRegisterServiceV1 userRegisterServiceV1;
    private final OAuthUserService oAuthUserService;
    private final PasswordEncoder encoder;

    @Deprecated
    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@Validated @RequestBody PasswordRegisterCommandV1 command) {
        command.setPassword(encoder.encode(command.getPassword()));
        var userInfo = userRegisterServiceV1.registerUser(command).convertToUserInfo();
        SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(userInfo));
        return ResponseEntity.ok().build();
    }

    @Deprecated
    @PostMapping("/social")
    public ResponseEntity<Void> registerOrLoginSocialUser(@Validated @RequestBody OAuthRegisterRequestDtoV1 oAuthRegisterRequestDtoV1) {
        var oAuthRegisterInfo = oAuthUserService.getOAuthRegisterInfo(oAuthRegisterRequestDtoV1);
        var userInfo = userRegisterServiceV1.getOrRegisterUserReturnInfo(oAuthRegisterInfo);
        SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(userInfo));
        return ResponseEntity.ok().build();
    }

}

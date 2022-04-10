package com.depromeet.archive.api.user;

import com.depromeet.archive.api.dto.user.OAuthRegisterDto;
import com.depromeet.archive.domain.user.UserService;
import com.depromeet.archive.domain.user.command.PasswordRegisterCommand;
import com.depromeet.archive.domain.user.info.UserInfo;
import com.depromeet.archive.infra.user.oauth.OAuthUserService;
import com.depromeet.archive.security.token.HttpAuthTokenSupport;
import com.depromeet.archive.security.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;
    private final OAuthUserService oAuthUserService;
    private final PasswordEncoder encoder;

    // JWT token provider
    private final TokenProvider tokenProvider;
    private final HttpAuthTokenSupport tokenSupport;

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid PasswordRegisterCommand command) {
        encryptPassword(command);
        userService.registerUser(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/social")
    public ResponseEntity<Void> registerOrLoginSocialUser(HttpServletResponse httpServletResponse,
                                                          @RequestParam("provider") String provider,
                                                          @RequestBody(required = false) OAuthRegisterDto oAuthRegisterDto) {
        var oAuthRegisterInfo = oAuthUserService.getOAuthRegisterInfo(provider, oAuthRegisterDto);
        var userInfo = userService.getOrRegisterUserReturnInfo(oAuthRegisterInfo);
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

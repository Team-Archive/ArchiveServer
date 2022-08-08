package site.archive.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.archive.api.resolver.annotation.RequestUser;
import site.archive.config.security.util.SecurityUtils;
import site.archive.domain.user.UserInfo;
import site.archive.dto.v1.archive.EmailDuplicateResponseDto;
import site.archive.dto.v1.auth.LoginCommand;
import site.archive.dto.v1.user.UserEmailRequestDto;
import site.archive.dto.v1.user.UserPasswordResetRequestDto;
import site.archive.service.user.UserAuthService;
import site.archive.service.user.UserService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserControllerV1 {

    private static final int TEMP_PASSWORD_LENGTH = 10;
    private final UserService userService;
    private final UserAuthService userAuthService;

    @SuppressWarnings(value = "all")
    @Operation(summary = "패스워드 유저 로그인")
    @PostMapping("/login")
    public void loginUser(@RequestBody LoginCommand loginCommand) {
        /*
        Execute password user login process by BodyCredentialAuthenticationFilter.
        This is just a class to print endpoint at swagger.
         */
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/unregister")
    public ResponseEntity<Void> unregisterUser(@RequestUser UserInfo user) {
        userService.deleteUser(user.getUserId());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "자기 정보 조회")
    @GetMapping("/info")
    public ResponseEntity<UserInfo> getUserInfo(@RequestUser UserInfo user) {
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "[NoAuth] 이메일 중복 검사")
    @GetMapping("/email/{email}")
    public ResponseEntity<EmailDuplicateResponseDto> checkDuplicatedEmail(@PathVariable String email) {
        var emailDuplicateResponseDto = new EmailDuplicateResponseDto(userService.existsEmail(email));
        return ResponseEntity.ok(emailDuplicateResponseDto);
    }

    @Operation(summary = "비밀번호 초기화 - 임시 비밀번호 발급")
    @PostMapping("/password/temporary")
    public ResponseEntity<Void> issueTemporaryPassword(@Validated @RequestBody UserEmailRequestDto userEmailRequestDto) {
        var temporaryPassword = SecurityUtils.generateRandomString(TEMP_PASSWORD_LENGTH);
        userAuthService.updateTemporaryPassword(userEmailRequestDto.getEmail(), temporaryPassword);
        return ResponseEntity.ok().build();
    }

    @Deprecated
    @Operation(summary = "비밀번호 초기화 - 새로운 비밀번호 설정")
    @PostMapping("/password/reset")
    public ResponseEntity<Void> resetPassword(@Validated @RequestBody UserPasswordResetRequestDto userPasswordResetRequestDto) {
        userAuthService.resetPassword(userPasswordResetRequestDto);
        return ResponseEntity.ok().build();
    }

}

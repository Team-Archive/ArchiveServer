package site.archive.web.api.v1;

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
import site.archive.domain.user.UserInfo;
import site.archive.dto.v1.archive.EmailDuplicateResponseDtoV1;
import site.archive.dto.v1.auth.LoginCommandV1;
import site.archive.dto.v1.user.UserEmailRequestDtoV1;
import site.archive.dto.v1.user.UserPasswordResetRequestDtoV1;
import site.archive.service.user.UserAuthService;
import site.archive.service.user.UserService;
import site.archive.web.api.resolver.annotation.RequestUser;
import site.archive.web.config.security.util.SecurityUtils;

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
    public void loginUser(@RequestBody LoginCommandV1 loginCommandV1) {
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

    @Deprecated
    @Operation(summary = "[Deprecated -> /api/v2/user/profile] 자기 정보 조회")
    @GetMapping("/info")
    public ResponseEntity<UserInfo> getUserInfo(@RequestUser UserInfo user) {
        return ResponseEntity.ok(user);
    }

    @Deprecated
    @Operation(summary = "[Deprecated -> /api/v2/user/duplicate/email] 이메일 중복 검사")
    @GetMapping("/email/{email}")
    public ResponseEntity<EmailDuplicateResponseDtoV1> checkDuplicatedEmail(@PathVariable String email) {
        var emailDuplicateResponseDto = new EmailDuplicateResponseDtoV1(userService.existsEmail(email));
        return ResponseEntity.ok(emailDuplicateResponseDto);
    }

    @Operation(summary = "비밀번호 초기화 - 임시 비밀번호 발급")
    @PostMapping("/password/temporary")
    public ResponseEntity<Void> issueTemporaryPassword(@Validated @RequestBody UserEmailRequestDtoV1 userEmailRequestDtoV1) {
        var temporaryPassword = SecurityUtils.generateRandomString(TEMP_PASSWORD_LENGTH);
        userAuthService.updateTemporaryPassword(userEmailRequestDtoV1.getEmail(), temporaryPassword);
        return ResponseEntity.ok().build();
    }

    @Deprecated
    @Operation(summary = "[Deprecated -> /api/v2/auth/password/reset] 비밀번호 초기화 - 새로운 비밀번호 설정")
    @PostMapping("/password/reset")
    public ResponseEntity<Void> resetPassword(@Validated @RequestBody UserPasswordResetRequestDtoV1 userPasswordResetRequestDtoV1) {
        userAuthService.resetPassword(userPasswordResetRequestDtoV1);
        return ResponseEntity.ok().build();
    }

}

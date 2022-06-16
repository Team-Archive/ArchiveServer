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
import site.archive.api.dto.archive.EmailDuplicateResponseDto;
import site.archive.api.dto.user.UserEmailRequestDto;
import site.archive.api.dto.user.UserPasswordResetRequestDto;
import site.archive.api.resolver.annotation.RequestUser;
import site.archive.domain.user.UserAuthService;
import site.archive.domain.user.UserService;
import site.archive.domain.user.info.UserInfo;
import site.archive.util.SecurityUtils;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserControllerV1 {

    private static final int TEMP_PASSWORD_LENGTH = 10;
    private final UserService userService;
    private final UserAuthService userAuthService;

    @DeleteMapping("/unregister")
    public ResponseEntity<Void> unregisterUser(@RequestUser UserInfo user) {
        userService.deleteUser(user.getUserId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfo> getUserInfo(@RequestUser UserInfo user) {
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "이메일 중복 검사")
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

    @Operation(summary = "비밀번호 초기화 - 새로운 비밀번호 설정")
    @PostMapping("/password/reset")
    public ResponseEntity<Void> resetPassword(@Validated @RequestBody UserPasswordResetRequestDto userPasswordResetRequestDto) {
        userAuthService.resetPassword(userPasswordResetRequestDto);
        return ResponseEntity.ok().build();
    }

}

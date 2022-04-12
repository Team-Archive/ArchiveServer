package com.depromeet.archive.api.user;

import com.depromeet.archive.api.dto.archive.EmailDuplicateResponseDto;
import com.depromeet.archive.api.resolver.annotation.RequestUser;
import com.depromeet.archive.domain.user.UserService;
import com.depromeet.archive.domain.user.info.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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

}

package site.archive.api.v2;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.archive.api.v1.dto.user.UserPasswordResetRequestDto;
import site.archive.domain.user.UserAuthService;

@RestController
@RequestMapping("/api/v2/auth")
@RequiredArgsConstructor
public class UserPasswordControllerV2 {

    private final UserAuthService userAuthService;

    @Operation(summary = "비밀번호 초기화 - 새로운 비밀번호 설정")
    @PostMapping("/password/reset")
    public ResponseEntity<Void> resetPassword(@Validated @RequestBody UserPasswordResetRequestDto userPasswordResetRequestDto) {
        userAuthService.resetPassword(userPasswordResetRequestDto);
        return ResponseEntity.ok().build();
    }

}

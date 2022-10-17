package site.archive.web.api.v2;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.archive.domain.user.UserInfo;
import site.archive.dto.v1.archive.EmailDuplicateResponseDto;
import site.archive.dto.v2.NicknameDuplicateResponseDto;
import site.archive.dto.v2.UserNicknameUpdateRequest;
import site.archive.service.user.UserService;
import site.archive.web.api.resolver.annotation.RequestUser;

@RestController
@RequestMapping("/api/v2/user")
@RequiredArgsConstructor
public class UserControllerV2 {

    private final UserService userService;

    @Operation(summary = "[NoAuth] 이메일 중복 검사")
    @GetMapping("/duplicate/email")
    public ResponseEntity<EmailDuplicateResponseDto> checkDuplicatedEmail(@RequestParam(value = "value") String email) {
        var emailDuplicateResponseDto = new EmailDuplicateResponseDto(userService.existsEmail(email));
        return ResponseEntity.ok(emailDuplicateResponseDto);
    }

    @Operation(summary = "[NoAuth] 닉네임 중복 검사")
    @GetMapping("/duplicate/nickname")
    public ResponseEntity<NicknameDuplicateResponseDto> checkDuplicatedNickname(@RequestParam(value = "value") String nickname) {
        var nicknameDuplicateResponseDto = new NicknameDuplicateResponseDto(userService.existsNickname(nickname));
        return ResponseEntity.ok(nicknameDuplicateResponseDto);
    }

    @Operation(summary = "프로필 닉네임 수정 (업데이트)")
    @PutMapping("/nickname")
    public ResponseEntity<Void> updateProfileNickname(@RequestUser UserInfo user,
                                                      @RequestBody UserNicknameUpdateRequest request) {
        userService.updateUserNickname(user.getUserId(), request.nickname());
        return ResponseEntity.noContent().build();
    }

}

package site.archive.web.api.v2;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.archive.domain.user.UserInfo;
import site.archive.dto.v1.archive.EmailDuplicateResponseDtoV1;
import site.archive.dto.v2.NicknameDuplicateResponseDto;
import site.archive.dto.v2.UserNicknameUpdateRequest;
import site.archive.service.user.UserService;
import site.archive.web.api.docs.swagger.UserControllerV2Docs;
import site.archive.web.api.resolver.annotation.RequestUser;

@RestController
@RequestMapping("/api/v2/user")
@RequiredArgsConstructor
public class UserControllerV2 implements UserControllerV2Docs {

    private final UserService userService;

    @GetMapping("/duplicate/email")
    public ResponseEntity<EmailDuplicateResponseDtoV1> checkDuplicatedEmail(@RequestParam(value = "value") String email) {
        var emailDuplicateResponseDto = new EmailDuplicateResponseDtoV1(userService.existsEmail(email));
        return ResponseEntity.ok(emailDuplicateResponseDto);
    }

    @GetMapping("/duplicate/nickname")
    public ResponseEntity<NicknameDuplicateResponseDto> checkDuplicatedNickname(@RequestParam(value = "value") String nickname) {
        var nicknameDuplicateResponseDto = new NicknameDuplicateResponseDto(userService.existsNickname(nickname));
        return ResponseEntity.ok(nicknameDuplicateResponseDto);
    }

    @PutMapping("/nickname")
    public ResponseEntity<Void> updateProfileNickname(@RequestUser UserInfo user,
                                                      @RequestBody UserNicknameUpdateRequest request) {
        userService.updateUserNickname(user.getUserId(), request.getNickname());
        return ResponseEntity.noContent().build();
    }

}

package site.archive.web.api.docs.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import site.archive.domain.user.UserInfo;
import site.archive.dto.v1.archive.EmailDuplicateResponseDtoV1;
import site.archive.dto.v2.NicknameDuplicateResponseDto;
import site.archive.dto.v2.UserNicknameUpdateRequest;

public interface UserControllerV2Docs {

    @Operation(summary = "[NoAuth] 이메일 중복 검사")
    ResponseEntity<EmailDuplicateResponseDtoV1> checkDuplicatedEmail(
        @Parameter(name = "value", description = "중복검사 할 이메일 주소") String email);

    @Operation(summary = "[NoAuth] 닉네임 중복 검사")
    ResponseEntity<NicknameDuplicateResponseDto> checkDuplicatedNickname(
        @Parameter(name = "value", description = "중복검사 할 닉네임") String nickname);

    @Operation(summary = "프로필 닉네임 수정 (업데이트)")
    ResponseEntity<Void> updateProfileNickname(UserInfo user, UserNicknameUpdateRequest request);

}

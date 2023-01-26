package site.archive.web.api.docs.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import site.archive.domain.user.UserInfo;
import site.archive.dto.v1.archive.EmailDuplicateResponseDtoV1;
import site.archive.dto.v1.auth.LoginCommandV1;
import site.archive.dto.v1.user.UserEmailRequestDtoV1;
import site.archive.dto.v1.user.UserPasswordResetRequestDtoV1;

public interface UserControllerV1Docs {

    @Operation(summary = "패스워드 유저 로그인")
    void loginUser(LoginCommandV1 loginCommandV1);

    @Operation(summary = "회원 탈퇴")
    ResponseEntity<Void> unregisterUser(UserInfo user);

    @Operation(summary = "[Deprecated -> /api/v2/user/profile] 자기 정보 조회")
    ResponseEntity<UserInfo> getUserInfo(UserInfo user);

    @Operation(summary = "[Deprecated -> /api/v2/user/duplicate/email] 이메일 중복 검사")
    ResponseEntity<EmailDuplicateResponseDtoV1> checkDuplicatedEmail(
        @Parameter(name = "email", description = "중복검사 하려는 이메일 주소") String email);

    @Operation(summary = "비밀번호 초기화 - 임시 비밀번호 발급")
    ResponseEntity<Void> issueTemporaryPassword(UserEmailRequestDtoV1 userEmailRequestDtoV1);

    @Operation(summary = "[Deprecated -> /api/v2/auth/password/reset] 비밀번호 초기화 - 새로운 비밀번호 설정")
    ResponseEntity<Void> resetPassword(UserPasswordResetRequestDtoV1 userPasswordResetRequestDtoV1);

}

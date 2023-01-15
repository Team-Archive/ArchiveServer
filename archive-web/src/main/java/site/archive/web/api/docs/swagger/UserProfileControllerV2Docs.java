package site.archive.web.api.docs.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import site.archive.domain.user.UserInfo;
import site.archive.dto.v1.user.SpecificUserDtoV1;

public interface UserProfileControllerV2Docs {

    @Operation(summary = "프로필 정보 조회")
    ResponseEntity<SpecificUserDtoV1> getUserProfileInfo(UserInfo user);

    @Operation(summary = "프로필 이미지 업로드 및 업데이트")
    ResponseEntity<Void> uploadProfileImage(UserInfo user,
                                            @Parameter(name = "image", description = "프로필 이미지 파일") MultipartFile imageFile);

    @Operation(summary = "프로필 이미지 제거")
    ResponseEntity<Void> removeProfileImage(UserInfo user);

}

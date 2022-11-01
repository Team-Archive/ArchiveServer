package site.archive.web.api.v2;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import site.archive.domain.user.UserInfo;
import site.archive.dto.v1.user.BaseUserDtoV1;
import site.archive.service.archive.ArchiveImageService;
import site.archive.service.user.UserProfileImageService;
import site.archive.service.user.UserService;
import site.archive.web.api.resolver.annotation.RequestUser;

import static site.archive.service.archive.ArchiveImageService.USER_PROFILE_IMAGE_DIRECTORY;

@RestController
@RequestMapping("/api/v2/user/profile")
@RequiredArgsConstructor
public class UserProfileControllerV2 {

    private final UserService userService;
    private final UserProfileImageService userProfileImageService;
    private final ArchiveImageService imageService;

    @Operation(summary = "프로필 정보 조회")
    @GetMapping
    public ResponseEntity<BaseUserDtoV1> getUserProfileInfo(@RequestUser UserInfo user) {
        return ResponseEntity.ok(userService.findSpecificUserById(user.getUserId()));
    }

    @Operation(summary = "프로필 이미지 업로드 및 업데이트")
    @PostMapping(path = "/image/upload",
                 consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> uploadProfileImage(@RequestUser UserInfo user,
                                                   @RequestParam("image") MultipartFile imageFile) {
        imageService.verifyImageFile(imageFile);

        var outdatedImageUri = userService.findUserById(user.getUserId()).getProfileImage();
        var imageUri = imageService.update(USER_PROFILE_IMAGE_DIRECTORY, outdatedImageUri, imageFile);

        userProfileImageService.updateUserProfileImage(user.getUserId(), imageUri);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "프로필 이미지 제거")
    @DeleteMapping(path = "/image/remove")
    public ResponseEntity<Void> removeProfileImage(@RequestUser UserInfo user) {
        var outdatedImageUri = userService.findUserById(user.getUserId()).getProfileImage();
        userProfileImageService.updateUserProfileImage(user.getUserId(), null);
        imageService.remove(outdatedImageUri);
        return ResponseEntity.noContent().build();
    }

}

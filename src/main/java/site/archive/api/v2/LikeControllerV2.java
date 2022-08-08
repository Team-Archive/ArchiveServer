package site.archive.api.v2;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.archive.api.resolver.annotation.RequestUser;
import site.archive.domain.user.UserInfo;
import site.archive.service.like.LikeService;

@RestController
@RequestMapping("/api/v2/archive")
@RequiredArgsConstructor
public class LikeControllerV2 {

    private final LikeService likeService;

    @Operation(summary = "좋아요 추가")
    @PostMapping("/{archiveId}/like")
    public ResponseEntity<Void> add(@RequestUser UserInfo user,
                                    @PathVariable Long archiveId) {
        likeService.save(user.getUserId(), archiveId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "좋아요 삭제")
    @DeleteMapping("/{archiveId}/like")
    public ResponseEntity<Void> delete(@RequestUser UserInfo user,
                                       @PathVariable Long archiveId) {
        likeService.delete(user.getUserId(), archiveId);
        return ResponseEntity.ok().build();
    }

}

package site.archive.web.api.v2;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.archive.domain.user.UserInfo;
import site.archive.dto.v2.LikesRequestDto;
import site.archive.dto.v2.UnlikesRequestDto;
import site.archive.service.like.LikeService;
import site.archive.web.api.resolver.annotation.RequestUser;

@RestController
@RequestMapping("/api/v2/archive/like")
@RequiredArgsConstructor
public class LikeControllerV2 {

    private final LikeService likeService;

    @Operation(summary = "좋아요 추가")
    @PostMapping("/{archiveId}")
    public ResponseEntity<Void> like(@RequestUser UserInfo user, @PathVariable Long archiveId) {
        likeService.save(user.getUserId(), archiveId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "좋아요 추가 (Bulk)")
    @PostMapping
    public ResponseEntity<Void> likeBulk(@RequestUser UserInfo user, @RequestBody LikesRequestDto likesRequest) {
        likeService.save(user.getUserId(), likesRequest.getArchiveIds());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "좋아요 삭제")
    @DeleteMapping("/{archiveId}")
    public ResponseEntity<Void> unlike(@RequestUser UserInfo user, @PathVariable Long archiveId) {
        likeService.delete(user.getUserId(), archiveId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "좋아요 삭제 (Bulk)")
    @DeleteMapping
    public ResponseEntity<Void> unlikeBulk(@RequestUser UserInfo user, @RequestBody UnlikesRequestDto unlikesRequest) {
        likeService.delete(user.getUserId(), unlikesRequest.getArchiveIds());
        return ResponseEntity.ok().build();
    }

}

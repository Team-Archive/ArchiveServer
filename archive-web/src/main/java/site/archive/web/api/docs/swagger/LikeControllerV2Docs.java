package site.archive.web.api.docs.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import site.archive.domain.user.UserInfo;
import site.archive.dto.v2.LikesRequestDto;
import site.archive.dto.v2.UnlikesRequestDto;

public interface LikeControllerV2Docs {

    @Operation(summary = "좋아요 추가")
    ResponseEntity<Void> like(UserInfo user,
                              @Parameter(name = "archiveId", description = "좋아요 할 아카이브 Index (Id)") Long archiveId);

    @Operation(summary = "좋아요 추가 (Bulk)")
    ResponseEntity<Void> likeBulk(UserInfo user,
                                  @Parameter(name = "likesRequest", description = "좋아요 할 아카이브 Index list") LikesRequestDto likesRequest);

    @Operation(summary = "좋아요 삭제")
    ResponseEntity<Void> unlike(UserInfo user,
                                @Parameter(name = "archiveId", description = "좋아요 취소할 아카이브 Index (Id)") Long archiveId);


    @Operation(summary = "좋아요 삭제 (Bulk)")
    ResponseEntity<Void> unlikeBulk(UserInfo user,
                                    @Parameter(name = "unlikesRequest", description = "좋아요 취소할 아카이브 Index list")
                                    UnlikesRequestDto unlikesRequest);

}

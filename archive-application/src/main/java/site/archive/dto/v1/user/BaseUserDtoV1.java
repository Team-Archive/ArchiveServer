package site.archive.dto.v1.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import site.archive.common.DateTimeUtil;
import site.archive.domain.user.BaseUser;
import site.archive.domain.user.UserRole;

import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class BaseUserDtoV1 {

    private final Long userId;
    private final String mailAddress;
    private final UserRole userRole;
    private final LocalDateTime createdAt;
    private final String profileImage;
    private final String nickname;

    // mutable field
    private String userType;

    public static BaseUserDtoV1 from(BaseUser baseUser) {
        return new BaseUserDtoV1(baseUser.getId(),
                                 baseUser.getMailAddress(),
                                 baseUser.getRole(),
                                 baseUser.getCreatedAt(),
                                 baseUser.getProfileImage(),
                                 baseUser.getNickname(),
                                 baseUser.getUserType());
    }

    public String getCreatedAt() {
        return DateTimeUtil.DATE_TIME_FORMATTER.format(this.createdAt);
    }

    public void updateSpecificUserType(String userType) {
        this.userType = userType;
    }

}

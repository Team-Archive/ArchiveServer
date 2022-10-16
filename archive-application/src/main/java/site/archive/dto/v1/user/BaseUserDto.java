package site.archive.dto.v1.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import site.archive.common.DateTimeUtil;
import site.archive.domain.user.BaseUser;
import site.archive.domain.user.UserRole;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class BaseUserDto {

    private final Long userId;
    private final String mailAddress;
    private final UserRole userRole;
    private final LocalDateTime createdAt;
    private final String profileImage;

    public static BaseUserDto from(BaseUser baseUser) {
        return new BaseUserDto(baseUser.getId(),
                               baseUser.getMailAddress(),
                               baseUser.getRole(),
                               baseUser.getCreatedAt(),
                               baseUser.getProfileImage());
    }

    public String getCreatedAt() {
        return DateTimeUtil.DATE_TIME_FORMATTER.format(this.createdAt);
    }

}

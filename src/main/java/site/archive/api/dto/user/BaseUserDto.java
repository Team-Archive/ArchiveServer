package site.archive.api.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import site.archive.domain.user.entity.BaseUser;
import site.archive.util.DateTimeUtil;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class BaseUserDto {

    private final Long userId;
    private final String mailAddress;
    private final LocalDateTime createdAt;

    public static BaseUserDto from(BaseUser baseUser) {
        return new BaseUserDto(baseUser.getUserId(), baseUser.getMailAddress(), baseUser.getCreatedAt());
    }

    public String getCreatedAt() {
        return DateTimeUtil.DATE_TIME_FORMATTER.format(this.createdAt);
    }

}

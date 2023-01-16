package site.archive.dto.v1.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import site.archive.common.DateTimeUtils;
import site.archive.domain.user.BaseUser;
import site.archive.domain.user.UserRole;

@RequiredArgsConstructor
@Getter
public class SpecificUserDtoV1 {

    private final Long userId;
    private final String mailAddress;
    private final UserRole userRole;
    private final String createdAt;
    private final String profileImage;
    private final String nickname;
    private final String userType;

    public static SpecificUserDtoV1 from(BaseUser baseUser) {
        var createdAt = DateTimeUtils.getDateTimeFormatter().format(baseUser.getCreatedAt());
        return new SpecificUserDtoV1(baseUser.getId(),
                                     baseUser.getMailAddress(),
                                     baseUser.getRole(),
                                     createdAt,
                                     baseUser.getProfileImage(),
                                     baseUser.getNickname(),
                                     baseUser.getUserType());
    }

}

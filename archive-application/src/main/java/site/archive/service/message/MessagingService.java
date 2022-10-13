package site.archive.service.message;

import site.archive.dto.v1.archive.ArchiveDto;
import site.archive.dto.v1.user.BaseUserDto;

public interface MessagingService {

    void sendUserRegisterMessage(BaseUserDto user, String registerType);

    void sendArchiveReportMessage(String userEmail, String reportReason, ArchiveDto archive);

}

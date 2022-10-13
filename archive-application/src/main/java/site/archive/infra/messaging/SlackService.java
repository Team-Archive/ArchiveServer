package site.archive.infra.messaging;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.block.Blocks;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.BlockCompositions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import site.archive.common.exception.infra.SlackException;
import site.archive.dto.v1.archive.ArchiveDto;
import site.archive.dto.v1.user.BaseUserDto;
import site.archive.infra.messaging.config.SlackProperty;
import site.archive.service.message.MessagingService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SlackService implements MessagingService {

    private final SlackProperty slackProperty;
    private final MethodsClient slackClient;

    @Value("${spring.profiles.active}")
    private String profile;

    public SlackService(final SlackProperty slackProperty) {
        this.slackProperty = slackProperty;
        this.slackClient = Slack.getInstance().methods(slackProperty.getToken());
    }

    @Override
    public void sendUserRegisterMessage(final BaseUserDto baseUser, final String registerType) {
        try {
            slackClient.chatPostMessage(req -> req
                                                   .channel(slackProperty.getChannel())
                                                   .blocks(Blocks.asBlocks(
                                                       Blocks.divider(),
                                                       Blocks.section(section -> section.text(BlockCompositions.markdownText(
                                                           ":tada: *새로운 유저가 찾아왔어요* :tada:"))),
                                                       Blocks.divider(),
                                                       Blocks.section(section -> section.text(BlockCompositions.markdownText(String.format(
                                                           ":factory: `환경`: %s",
                                                           profile)))),
                                                       Blocks.divider(),
                                                       Blocks.section(section -> section.text(BlockCompositions.markdownText(String.format(
                                                           ":sparkles: `회원가입 타입`: " +
                                                           "%s",
                                                           registerType)))),
                                                       Blocks.section(section -> section.text(BlockCompositions.markdownText(String.format(
                                                           ":man-raising-hand::skin-tone-2: `유저 인덱스`: %s",
                                                           baseUser.getUserId())))),
                                                       Blocks.section(section -> section.text(BlockCompositions.markdownText(String.format(
                                                           ":email: `이메일`: %s",
                                                           baseUser.getMailAddress())))),
                                                       Blocks.section(section -> section.text(BlockCompositions.markdownText(String.format(
                                                           ":date: `가입시간`: %s",
                                                           baseUser.getCreatedAt()))))
                                                   )));
        } catch (IOException | SlackApiException e) {
            throw new SlackException(e.getMessage());
        }
    }

    @Override
    public void sendArchiveReportMessage(final String userEmail, final String reportReason, final ArchiveDto archive) {
        var message = new ArrayList<>(List.of(Blocks.divider(),
                                              Blocks.section(section -> section.text(BlockCompositions.markdownText(
                                                  ":loudspeaker: *개시물 신고가 들어왔어요* :loudspeaker:"))),
                                              Blocks.divider(),
                                              Blocks.section(section -> section.text(BlockCompositions.markdownText(String.format(
                                                  ":factory: `환경`: %s", profile)))),
                                              Blocks.divider(),
                                              Blocks.section(section -> section.text(BlockCompositions.markdownText(String.format(
                                                  ":clipboard: `아카이브 ID`: %s, `아카이브 제목`: %s",
                                                  archive.getArchiveId(),
                                                  archive.getName())))),
                                              Blocks.section(section -> section.text(BlockCompositions.markdownText(String.format(
                                                  ":frame_with_picture: `아카이브 메인 이미지`: %s", archive.getMainImage())))),
                                              Blocks.section(section -> section.text(BlockCompositions.markdownText(String.format(
                                                  ":female-police-officer::skin-tone-2: `신고자`: %s", userEmail)))),
                                              Blocks.section(section -> section.text(BlockCompositions.markdownText(String.format(
                                                  ":question: `신고 이유`: %s", reportReason))))));
        addArchiveImagesToMessageIfExists(archive, message);

        try {
            slackClient.chatPostMessage(req -> req.channel(slackProperty.getReportChannel())
                                                  .blocks(message));
        } catch (IOException | SlackApiException e) {
            throw new SlackException(e.getMessage());
        }
    }

    private void addArchiveImagesToMessageIfExists(ArchiveDto archive, ArrayList<LayoutBlock> message) {
        var archiveImages = archive.getImages();
        if (!archiveImages.isEmpty()) {
            message.add(Blocks.divider());
            message.add(Blocks.section(section -> section.text(BlockCompositions.markdownText(
                ":point_down::skin-tone-2: *아카이브 내용* :point_down::skin-tone-2:"))));
            archiveImages.forEach(archiveImageDto -> {
                message.add(Blocks.divider());
                message.add(Blocks.section(section -> section.text(BlockCompositions.markdownText(String.format(
                    "- `아카이브 상세 글`: %s", archiveImageDto.getReview())))));
                message.add(Blocks.section(section -> section.text(BlockCompositions.markdownText(String.format(
                    "- `아카이브 상세 이미지`: %s", archiveImageDto.getImage())))));
            });
        }
    }

}

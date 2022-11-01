package site.archive.infra.messaging;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.block.Blocks;
import com.slack.api.model.block.composition.BlockCompositions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import site.archive.common.exception.infra.SlackException;
import site.archive.dto.v1.user.BaseUserDtoV1;
import site.archive.infra.messaging.config.SlackProperty;
import site.archive.service.message.MessagingService;

import java.io.IOException;

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
    public void sendUserRegisterMessage(final BaseUserDtoV1 baseUser, final String registerType) {
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

}

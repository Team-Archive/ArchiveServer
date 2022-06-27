package site.archive.infra.messaging;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import site.archive.api.v1.dto.user.BaseUserDto;
import site.archive.domain.common.MessagingService;
import site.archive.exception.infra.SlackException;
import site.archive.infra.messaging.config.SlackProperty;

import java.io.IOException;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.divider;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;

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
                                                   .blocks(asBlocks(
                                                       divider(),
                                                       section(section -> section.text(markdownText(":tada: *새로운 유저가 찾아왔어요* :tada:"))),
                                                       divider(),
                                                       section(section -> section.text(markdownText(String.format(":factory: `환경`: %s",
                                                                                                                  profile)))),
                                                       divider(),
                                                       section(section -> section.text(markdownText(String.format(":sparkles: `회원가입 타입`: " +
                                                                                                                  "%s",
                                                                                                                  registerType)))),
                                                       section(section -> section.text(markdownText(String.format(
                                                           ":man-raising-hand::skin-tone-2: `유저 인덱스`: %s",
                                                           baseUser.getUserId())))),
                                                       section(section -> section.text(markdownText(String.format(":email: `이메일`: %s",
                                                                                                                  baseUser.getMailAddress())))),
                                                       section(section -> section.text(markdownText(String.format(":date: `가입시간`: %s",
                                                                                                                  baseUser.getCreatedAt()))))
                                                   )));
        } catch (IOException | SlackApiException e) {
            throw new SlackException(e.getMessage());
        }
    }

}

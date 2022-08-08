package site.archive.api.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import site.archive.domain.archive.Emotion;
import site.archive.domain.archive.custom.ArchiveCommunityTimeSortType;
import site.archive.domain.archive.custom.ArchivePageable;

import javax.validation.constraints.NotNull;

public class ArchivePageableArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String EMOTION_KEY = "emotion";
    private static final String LAST_ARCHIVE_DATETIME_KET = "lastArchiveDateTime";
    private static final String LAST_ARCHIVE_ID_KET = "lastArchiveId";


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return ArchivePageable.class.equals(parameter.getParameterType());
    }

    @Override
    public ArchivePageable resolveArgument(@NotNull MethodParameter parameter, ModelAndViewContainer mavContainer,
                                           NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        var sortType = ArchiveCommunityTimeSortType.of(webRequest.getParameter("sortType"));

        var emotionParameter = webRequest.getParameter(EMOTION_KEY);
        var emotion = emotionParameter != null
                      ? Emotion.valueOf(emotionParameter.toUpperCase()) : null;

        var lastArchiveDateTimeParameter = webRequest.getParameter(LAST_ARCHIVE_DATETIME_KET);
        var lastArchiveDateTime = lastArchiveDateTimeParameter != null
                                  ? Long.valueOf(lastArchiveDateTimeParameter) : null;

        var lastArchiveIdParameter = webRequest.getParameter(LAST_ARCHIVE_ID_KET);
        var lastArchiveId = lastArchiveIdParameter != null
                            ? Long.valueOf(lastArchiveIdParameter) : null;

        return new ArchivePageable(sortType, emotion, lastArchiveDateTime, lastArchiveId);
    }

}

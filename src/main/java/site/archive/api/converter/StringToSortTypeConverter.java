package site.archive.api.converter;

import org.springframework.core.convert.converter.Converter;
import site.archive.domain.archive.ArchiveCommunityTimeSortType;

public class StringToSortTypeConverter implements Converter<String, ArchiveCommunityTimeSortType> {

    @Override
    public ArchiveCommunityTimeSortType convert(String source) {
        return ArchiveCommunityTimeSortType.of(source);
    }

}

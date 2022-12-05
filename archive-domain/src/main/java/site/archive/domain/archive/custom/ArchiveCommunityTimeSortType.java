package site.archive.domain.archive.custom;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.Getter;
import site.archive.common.DateTimeUtil;
import site.archive.domain.archive.Archive;
import site.archive.domain.archive.QArchive;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum ArchiveCommunityTimeSortType {

    CREATED_AT("createdAt") {
        @Override
        public BooleanExpression getLtWhere(QArchive archive, final long milli) {
            var lastCreatedAtOfPage = DateTimeUtil.fromMilli(milli).toLocalDateTime();
            return archive.createdAt.lt(lastCreatedAtOfPage);
        }

        @Override
        public BooleanExpression getEqWhere(QArchive archive, long milli) {
            var lastCreatedAtOfPage = DateTimeUtil.fromMilli(milli).toLocalDateTime();
            return archive.createdAt.eq(lastCreatedAtOfPage);
        }

        @Override
        public OrderSpecifier<?> getOrderBy(QArchive archive) {
            return archive.createdAt.desc();
        }

        @Override
        public long convertToMillis(Archive archive) {
            return archive.getCreatedAt().atZone(DateTimeUtil.ASIA_SEOUL_ZONE).toInstant().toEpochMilli();
        }
    },
    WATCHED_ON("watchedOn") {
        @Override
        public BooleanExpression getLtWhere(QArchive archive, final long milli) {
            var lastWatchedOnOfPage = DateTimeUtil.fromMilli(milli).toLocalDate();
            return archive.watchedOn.lt(lastWatchedOnOfPage);
        }

        @Override
        public BooleanExpression getEqWhere(QArchive archive, long milli) {
            var lastWatchedOnOfPage = DateTimeUtil.fromMilli(milli).toLocalDate();
            return archive.watchedOn.eq(lastWatchedOnOfPage);
        }

        @Override
        public OrderSpecifier<?> getOrderBy(QArchive archive) {
            return archive.watchedOn.desc();
        }

        @Override
        public long convertToMillis(Archive archive) {
            return archive.getWatchedOn().atStartOfDay(DateTimeUtil.ASIA_SEOUL_ZONE).toInstant().toEpochMilli();
        }
    };

    private static final Map<String, ArchiveCommunityTimeSortType> sortTypeMap = new HashMap<>();

    static {
        Arrays.stream(values())
              .forEach(sortType -> sortTypeMap.put(sortType.getFieldName(), sortType));
    }

    private final String fieldName;

    ArchiveCommunityTimeSortType(String fieldName) {
        this.fieldName = fieldName;
    }

    public static ArchiveCommunityTimeSortType of(String fieldName) {
        var sortType = sortTypeMap.get(fieldName);
        if (sortType == null) {
            throw new IllegalArgumentException("SortType 값이 올바르지 않습니다");
        }
        return sortType;
    }

    public abstract BooleanExpression getLtWhere(QArchive archive, final long milli);

    public abstract BooleanExpression getEqWhere(QArchive archive, final long milli);

    public abstract OrderSpecifier<?> getOrderBy(QArchive archive);

    public abstract long convertToMillis(Archive archive);

}

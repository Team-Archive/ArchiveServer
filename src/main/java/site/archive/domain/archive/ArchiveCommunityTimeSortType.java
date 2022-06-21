package site.archive.domain.archive;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.Getter;
import site.archive.domain.archive.entity.Archive;
import site.archive.domain.archive.entity.QArchive;
import site.archive.util.DateTimeUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum ArchiveCommunityTimeSortType {

    CREATED_AT("createdAt") {
        @Override
        public BooleanExpression getLtWhereCondition(QArchive archive, final long milli) {
            var lastCreatedAtOfPage = DateTimeUtil.fromMilli(milli).toLocalDateTime();
            return archive.createdAt.lt(lastCreatedAtOfPage);
        }

        @Override
        public BooleanExpression getEqWhereCondition(QArchive archive, long milli) {
            var lastCreatedAtOfPage = DateTimeUtil.fromMilli(milli).toLocalDateTime();
            return archive.createdAt.eq(lastCreatedAtOfPage);
        }

        @Override
        public OrderSpecifier<?> getOrderCondition(QArchive archive) {
            return archive.createdAt.desc();
        }

        @Override
        public long getMilli(Archive archive) {
            return archive.getCreatedAt().atZone(DateTimeUtil.ASIA_ZONE).toInstant().toEpochMilli();
        }
    },
    WATCHED_ON("watchedOn") {
        @Override
        public BooleanExpression getLtWhereCondition(QArchive archive, final long milli) {
            var lastWatchedOnOfPage = DateTimeUtil.fromMilli(milli).toLocalDate();
            return archive.watchedOn.lt(lastWatchedOnOfPage);
        }

        @Override
        public BooleanExpression getEqWhereCondition(QArchive archive, long milli) {
            var lastWatchedOnOfPage = DateTimeUtil.fromMilli(milli).toLocalDate();
            return archive.watchedOn.eq(lastWatchedOnOfPage);
        }

        @Override
        public OrderSpecifier<?> getOrderCondition(QArchive archive) {
            return archive.watchedOn.desc();
        }

        @Override
        public long getMilli(Archive archive) {
            return archive.getWatchedOn().atStartOfDay(DateTimeUtil.ASIA_ZONE).toInstant().toEpochMilli();
        }
    };

    private static final Map<String, ArchiveCommunityTimeSortType> sortTypeMap = new HashMap<>();
    private final String fieldName;

    ArchiveCommunityTimeSortType(String fieldName) {
        this.fieldName = fieldName;
    }

    static {
        Arrays.stream(values())
              .forEach(sortType -> sortTypeMap.put(sortType.getFieldName(), sortType));
    }

    public abstract BooleanExpression getLtWhereCondition(QArchive archive, final long milli);

    public abstract BooleanExpression getEqWhereCondition(QArchive archive, final long milli);

    public abstract OrderSpecifier<?> getOrderCondition(QArchive archive);

    public abstract long getMilli(Archive archive);

    public static ArchiveCommunityTimeSortType of(String fieldName) {
        var sortType = sortTypeMap.get(fieldName);
        if (sortType == null) {
            throw new IllegalArgumentException("SortType 값이 올바르지 않습니다");
        }
        return sortType;
    }

}

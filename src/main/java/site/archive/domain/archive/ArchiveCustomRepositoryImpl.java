package site.archive.domain.archive;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.archive.domain.archive.entity.Archive;
import site.archive.domain.archive.entity.Emotion;

import java.util.List;

import static site.archive.domain.archive.entity.QArchive.archive;

@Repository
@RequiredArgsConstructor
public class ArchiveCustomRepositoryImpl implements ArchiveCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Archive> findFirstPage(ArchiveCommunityTimeSortType timeSortType, Emotion emotion, int pageElementSize) {
        var firstPageWhereCondition = addEmotionWhereConditionWhenEmotionExist(archive.isPublic.eq(true), emotion);
        var orderByTimeSortTypeDesc = timeSortType.getOrderCondition(archive);
        var orderByArchiveIdDesc = archive.id.desc();
        return jpaQueryFactory.selectFrom(archive)
                              .innerJoin(archive.author).fetchJoin()
                              .where(firstPageWhereCondition)
                              .orderBy(orderByTimeSortTypeDesc,
                                       orderByArchiveIdDesc)
                              .limit(pageElementSize)
                              .fetch();
    }

    @Override
    public List<Archive> findNextPage(ArchiveCommunityTimeSortType timeSortType,
                                      Emotion emotion,
                                      Long lastSeenArchiveDateMilli,
                                      Long lastSeenArchiveId,
                                      int pageElementSize) {
        var nextPageWhereCondition = getNextPageWhereCondition(timeSortType, emotion, lastSeenArchiveDateMilli, lastSeenArchiveId);
        var orderByTimeSortTypeDesc = timeSortType.getOrderCondition(archive);
        var orderByArchiveIdDesc = archive.id.desc();
        return jpaQueryFactory.selectFrom(archive)
                              .innerJoin(archive.author).fetchJoin()
                              .where(nextPageWhereCondition)
                              .orderBy(orderByTimeSortTypeDesc,
                                       orderByArchiveIdDesc)
                              .limit(pageElementSize)
                              .fetch();
    }

    /**
     * TimeSortType에 해당하는 필드의 값이 lastSeenArchiveDateMilli 값보다 작거나,
     * 같은 경우, archiveId에 해당하는 필드의 값이 lastSeenArchiveId 값보다 작아야 한다.
     * 그리고 public Archive만 조회하며, Emotion 필터가 설정된 경우 주어진 Emotion의 archive만 조회한다.
     *
     * @param timeSortType             시간 정렬 타입
     * @param emotion                  감정 필터링 타입
     * @param lastSeenArchiveDateMilli 현재 페이지의 마지막 시간 milli
     * @param lastSeenArchiveId        현재 페이지의 마지막 archive id
     * @return 위 조건에 해당하는 Where 조건식
     */
    private BooleanExpression getNextPageWhereCondition(ArchiveCommunityTimeSortType timeSortType,
                                                        Emotion emotion,
                                                        Long lastSeenArchiveDateMilli,
                                                        Long lastSeenArchiveId) {
        var timeSortTypeLtWhereCondition = timeSortType.getLtWhereCondition(archive, lastSeenArchiveDateMilli);
        var timeSortTypeEqWhereCondition = timeSortType.getEqWhereCondition(archive, lastSeenArchiveDateMilli);
        var archiveIdLtWhereCondition = archive.id.lt(lastSeenArchiveId);
        var timeSortTypeWhereCondition = timeSortTypeLtWhereCondition
                                             .or(timeSortTypeEqWhereCondition.and(archiveIdLtWhereCondition));
        var publicArchiveWhereCondition = archive.isPublic.eq(true);
        var whereCondition = timeSortTypeWhereCondition.and(publicArchiveWhereCondition);
        return addEmotionWhereConditionWhenEmotionExist(whereCondition, emotion);
    }

    private BooleanExpression addEmotionWhereConditionWhenEmotionExist(BooleanExpression whereCondition,
                                                                       Emotion emotion) {
        return emotion == null ? whereCondition : whereCondition.and(archive.emotion.eq(emotion));
    }

}

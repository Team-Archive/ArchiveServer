package site.archive.domain.archive.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.archive.common.DateTimeUtil;
import site.archive.domain.archive.Archive;
import site.archive.domain.archive.Emotion;
import site.archive.domain.archive.QArchive;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArchiveCustomRepositoryImpl implements ArchiveCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Archive> findFirstPageByAuthorId(Long authorId,
                                                 ArchivePageable pageable,
                                                 int pageElementSize) {
        var archiveQuery = archiveSelectQueryWithAuthor(pageable);
        var whereAuthorId = QArchive.archive.author.id.eq(authorId);
        return whereEmotionIfExists(archiveQuery, pageable.getEmotion())
                   .where(whereAuthorId)
                   .limit(pageElementSize)
                   .fetch();
    }

    @Override
    public List<Archive> findNextPageByAuthorId(Long authorId,
                                                ArchivePageable pageable,
                                                int pageElementSize) {
        var archiveQuery = archiveSelectQueryWithAuthor(pageable);
        var whereAuthorId = QArchive.archive.author.id.eq(authorId);
        return whereEmotionIfExists(archiveQuery, pageable.getEmotion())
                   .where(whereAuthorId, whereNextPage(pageable))
                   .limit(pageElementSize)
                   .fetch();
    }

    @Override
    public List<Archive> findFirstPageOnlyPublic(ArchivePageable pageable, int pageElementSize) {
        var archiveQuery = archiveSelectQueryWithAuthor(pageable);
        var whereOnlyPublic = QArchive.archive.isPublic.eq(true);
        return whereEmotionIfExists(archiveQuery, pageable.getEmotion())
                   .where(whereOnlyPublic)
                   .limit(pageElementSize)
                   .fetch();
    }

    @Override
    public List<Archive> findNextPageOnlyPublic(ArchivePageable pageable, int pageElementSize) {
        var archiveQuery = archiveSelectQueryWithAuthor(pageable);
        var whereOnlyPublic = QArchive.archive.isPublic.eq(true);
        return whereEmotionIfExists(archiveQuery, pageable.getEmotion())
                   .where(whereOnlyPublic, whereNextPage(pageable))
                   .limit(pageElementSize)
                   .fetch();
    }

    @Override
    public List<Archive> findByIdInWithLike(List<Long> archiveIds, ArchivePageable pageable) {
        var archiveQuery = archiveSelectQueryWithAuthor(pageable)
                               .leftJoin(QArchive.archive.likes).fetchJoin();
        return archiveQuery.where(QArchive.archive.id.in(archiveIds))
                           .distinct()
                           .fetch();
    }

    @Override
    public long countArchiveOfCurrentMonthByAuthorId(Long authorId) {
        var firstDateOfCurrentMonth = DateTimeUtil.firstDateTimeOfMonth();
        var firstDateOfNextMonth = firstDateOfCurrentMonth.plusMonths(1);
        var currentMonthWhere = QArchive.archive.createdAt.goe(firstDateOfCurrentMonth)
                                                          .and(QArchive.archive.createdAt.lt(firstDateOfNextMonth));
        return jpaQueryFactory.selectFrom(QArchive.archive)
                              .innerJoin(QArchive.archive.author).fetchJoin()
                              .where(QArchive.archive.author.id.eq(authorId), currentMonthWhere)
                              .stream().count();
    }

    /**
     * 기본 Archive page select query
     * - Join User table
     * - order by TimeSortType, Archive id
     */
    private JPAQuery<Archive> archiveSelectQueryWithAuthor(ArchivePageable pageable) {
        var timeSortTypeDescOrderBy = pageable.getSortType().getOrderBy(QArchive.archive);
        var archiveIdDescOrderBy = QArchive.archive.id.desc();
        return jpaQueryFactory.selectFrom(QArchive.archive)
                              .innerJoin(QArchive.archive.author).fetchJoin()
                              .orderBy(timeSortTypeDescOrderBy, archiveIdDescOrderBy);
    }

    /**
     * Emotion이 있는 경우(Not null), where 조건 절을 추가한다.
     */
    private JPAQuery<Archive> whereEmotionIfExists(JPAQuery<Archive> jpaQuery,
                                                   Emotion emotion) {
        return emotion == null ? jpaQuery : jpaQuery.where(QArchive.archive.emotion.eq(emotion));
    }

    /**
     * TimeSortType에 해당하는 필드의 값이 lastSeenArchiveDateMilli 값보다 작거나,
     * 같은 경우, archiveId에 해당하는 필드의 값이 lastSeenArchiveId 값보다 작아야 한다.
     */
    private BooleanExpression whereNextPage(ArchivePageable pageable) {
        var timeSortTypeLtWhere = pageable.getSortType().getLtWhere(QArchive.archive, pageable.getLastArchiveDateTime());
        var timeSortTypeEqWhere = pageable.getSortType().getEqWhere(QArchive.archive, pageable.getLastArchiveDateTime());
        var archiveIdLtWhere = QArchive.archive.id.lt(pageable.getLastArchiveId());
        return timeSortTypeLtWhere.or(timeSortTypeEqWhere.and(archiveIdLtWhere));
    }

}

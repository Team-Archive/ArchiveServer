package site.archive.domain.archive;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
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
    public List<Archive> findFirstPageOnlyPublic(ArchivePageable pageable, int pageElementSize) {
        var archiveQuery = baseArchiveSelectQuery(pageable);
        var onlyPublicWhere = archive.isPublic.eq(true);
        return whereEmotionIfExists(archiveQuery, pageable.getEmotion())
                   .where(onlyPublicWhere)
                   .limit(pageElementSize)
                   .fetch();
    }

    @Override
    public List<Archive> findNextPageOnlyPublic(ArchivePageable pageable, int pageElementSize) {
        var archiveQuery = baseArchiveSelectQuery(pageable);
        var onlyPublicWhere = archive.isPublic.eq(true);
        return whereEmotionIfExists(archiveQuery, pageable.getEmotion())
                   .where(onlyPublicWhere, getNextPageWhere(pageable))
                   .limit(pageElementSize)
                   .fetch();
    }

    /**
     * 기본 Archive page select query
     * - Join User table
     * - order by TimeSortType, Archive id
     */
    private JPAQuery<Archive> baseArchiveSelectQuery(ArchivePageable pageable) {
        var timeSortTypeDescOrderBy = pageable.getSortType().getOrderBy(archive);
        var archiveIdDescOrderBy = archive.id.desc();
        return jpaQueryFactory.selectFrom(archive)
                              .innerJoin(archive.author).fetchJoin()
                              .orderBy(timeSortTypeDescOrderBy, archiveIdDescOrderBy);
    }

    /**
     * Emotion이 있는 경우(Not null), where 조건 절을 추가한다.
     */
    private JPAQuery<Archive> whereEmotionIfExists(JPAQuery<Archive> jpaQuery,
                                                   Emotion emotion) {
        return emotion == null ? jpaQuery : jpaQuery.where(archive.emotion.eq(emotion));
    }

    /**
     * TimeSortType에 해당하는 필드의 값이 lastSeenArchiveDateMilli 값보다 작거나,
     * 같은 경우, archiveId에 해당하는 필드의 값이 lastSeenArchiveId 값보다 작아야 한다.
     */
    private BooleanExpression getNextPageWhere(ArchivePageable pageable) {
        var timeSortTypeLtWhere = pageable.getSortType().getLtWhere(archive, pageable.getLastArchiveDateTime());
        var timeSortTypeEqWhere = pageable.getSortType().getEqWhere(archive, pageable.getLastArchiveDateTime());
        var archiveIdLtWhere = archive.id.lt(pageable.getLastArchiveId());
        return timeSortTypeLtWhere.or(timeSortTypeEqWhere.and(archiveIdLtWhere));
    }

}

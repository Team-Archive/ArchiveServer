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
    public List<Archive> findFirstPage(ArchivePageable archivePageable, int pageElementSize) {
        var firstPageWhere = addEmotionWhereWhenEmotionExist(archive.isPublic.eq(true),
                                                             archivePageable.getEmotion());
        var timeSortTypeDescOrderBy = archivePageable.getSortType().getOrderBy(archive);
        var archiveIdDescOrderBy = archive.id.desc();
        return jpaQueryFactory.selectFrom(archive)
                              .innerJoin(archive.author).fetchJoin()
                              .where(firstPageWhere)
                              .orderBy(timeSortTypeDescOrderBy, archiveIdDescOrderBy)
                              .limit(pageElementSize)
                              .fetch();
    }

    @Override
    public List<Archive> findNextPage(ArchivePageable archivePageable, int pageElementSize) {
        var nextPageWhere = getNextPageWhere(archivePageable);
        var timeSortTypeDescOrderBy = archivePageable.getSortType().getOrderBy(archive);
        var archiveIdDescOrderBy = archive.id.desc();
        return jpaQueryFactory.selectFrom(archive)
                              .innerJoin(archive.author).fetchJoin()
                              .where(nextPageWhere)
                              .orderBy(timeSortTypeDescOrderBy, archiveIdDescOrderBy)
                              .limit(pageElementSize)
                              .fetch();
    }

    /**
     * TimeSortType에 해당하는 필드의 값이 lastSeenArchiveDateMilli 값보다 작거나,
     * 같은 경우, archiveId에 해당하는 필드의 값이 lastSeenArchiveId 값보다 작아야 한다.
     * 그리고 public Archive만 조회하며, Emotion 필터가 설정된 경우 주어진 Emotion의 archive만 조회한다.
     */
    private BooleanExpression getNextPageWhere(ArchivePageable archivePageable) {
        var timeSortTypeLtWhere = archivePageable.getSortType().getLtWhere(archive, archivePageable.getLastArchiveDateTime());
        var timeSortTypeEqWhere = archivePageable.getSortType().getEqWhere(archive, archivePageable.getLastArchiveDateTime());
        var archiveIdLtWhere = archive.id.lt(archivePageable.getLastArchiveId());
        var timeSortTypeWhere = timeSortTypeLtWhere
                                    .or(timeSortTypeEqWhere.and(archiveIdLtWhere));
        var publicArchiveWhere = archive.isPublic.eq(true);
        var where = timeSortTypeWhere.and(publicArchiveWhere);
        return addEmotionWhereWhenEmotionExist(where, archivePageable.getEmotion());
    }

    private BooleanExpression addEmotionWhereWhenEmotionExist(BooleanExpression where,
                                                              Emotion emotion) {
        return emotion == null ? where : where.and(archive.emotion.eq(emotion));
    }

}

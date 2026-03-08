package content.malgn.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import content.malgn.domain.Content;
import content.malgn.domain.QContent;
import content.malgn.domain.QMember;
import content.malgn.dto.content.SearchContentListCond;
import content.malgn.enums.ContentSortType;
import content.malgn.enums.DeleteStatus;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 커스텀 콘텐츠 레파지토리 구현체 클래스
 */
public class ContentRepositoryImpl implements ContentRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public ContentRepositoryImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<Content> pageByCond(SearchContentListCond cond, Pageable pageable) {

        QContent content = QContent.content;
        QMember member = QMember.member;

        BooleanBuilder builder = new BooleanBuilder();

        //활성화 된 게시글(삭제 x)
        builder.and(content.deleteStatus.eq(DeleteStatus.UNDELETED));

        //회원 이름으로 검색
        if(cond.getMemberName() != null && !cond.getMemberName().isBlank()) {
            builder.and(member.name.containsIgnoreCase(cond.getMemberName()));
        }

        //콘텐츠 제목으로 검색
        if(cond.getTitle() != null && !cond.getTitle().isBlank()) {
            builder.and(content.title.containsIgnoreCase(cond.getTitle()));
        }

        //정렬
        OrderSpecifier<?> orderSpecifier;
        if(cond.getSortType()== ContentSortType.OLDEST){
            orderSpecifier = content.createdDate.asc();
        }
        else if (cond.getSortType()==ContentSortType.HIGH_VIEW) {
            orderSpecifier = content.viewCount.desc();
        }
        else {
            orderSpecifier = content.createdDate.desc();
        }


        List<Content> result = query.
                select(content)
                .from(content)
                .join(content.member, member)
                .where(builder)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = query.select(content.count())
                .from(content)
                .join(content.member, member)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(result, pageable, total != null ? total : 0);


    }
}

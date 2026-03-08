package content.malgn.repository;

import content.malgn.domain.Content;
import content.malgn.dto.content.SearchContentListCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 콘텐츠 레파지토리 커스텀 (query dsl)
 */
public interface ContentRepositoryCustom {

    Page<Content> pageByCond(SearchContentListCond cond , Pageable pageable);

}

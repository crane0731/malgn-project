package content.malgn.dto.content;

import content.malgn.enums.ContentSortType;
import lombok.Getter;
import lombok.Setter;

/**
 * 콘텐츠 목록 조회 검색 조건 DTO
 */
@Getter
@Setter
public class SearchContentListCond {

    private String memberName;//회원 이름
    private String title;//제목

    private ContentSortType sortType;//정렬 조건

    /**
     * [생성 메서드]
     * @param memberName 회원 이름
     * @param title 제목
     * @param sortType 정렬 조건
     * @return SearchContentListCond
     */
    public static SearchContentListCond create(String memberName, String title, ContentSortType sortType) {
        SearchContentListCond cond = new SearchContentListCond();
        cond.setMemberName(memberName);
        cond.setTitle(title);
        cond.setSortType(sortType);
        return cond;
    }

    /**
     * 객체가 비어있는지 확인하는 메서드
     * @return boolean
     */
    public boolean isEmpty() {
        return title == null
                && memberName == null
                && sortType == null;
    }
}

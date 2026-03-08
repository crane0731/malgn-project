package content.malgn.dto.content;

import lombok.Getter;
import lombok.Setter;

/**
 * 콘텐츠 목록 응답 DTO
 */
@Getter
@Setter
public class ContentListResponseDto {

    private Long memberId;//회원 PK
    private String memberName;//회원 이름

    private Long contentId;//콘텐츠 아이디
    private String title;//제목
    private Long viewCount;//조회수
    private String createdDate;//생성일

}

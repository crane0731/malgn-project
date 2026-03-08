package content.malgn.dto.content;

import content.malgn.domain.Content;
import content.malgn.utils.DateFormatUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * 콘텐츠 상세 조회 응답 DTO
 */
@Getter
@Setter
public class ContentDetailsResponseDto {

    private Long memberId;//회원 PK
    private String memberEmail;//회원 이메일
    private String memberName;//회원 이름

    private Long contentId;//콘텐츠 PK
    private String title;//제목
    private String description;//내용
    private Long viewCount;// 조회수

    private String createdDate;//생성일
    private String lastModifiedDate;//마지막 수정일

    /**
     * [생성 메서드]
     * @param content 콘텐츠
     * @return  ContentDetailsResponseDto
     */
    public static ContentDetailsResponseDto create(Content content) {
        ContentDetailsResponseDto dto = new ContentDetailsResponseDto();
        dto.setMemberId(content.getMember().getId());
        dto.setMemberEmail(content.getMember().getEmail());
        dto.setMemberName(content.getMember().getName());

        dto.setContentId(content.getId());
        dto.setTitle(content.getTitle());

        if(content.getDescription() == null) {
            dto.setDescription("콘텐츠 내용이 없습니다.");
        }

        dto.setDescription(content.getDescription());
        dto.setViewCount(content.getViewCount());

        dto.setCreatedDate(DateFormatUtil.DateFormat(content.getCreatedDate()));
        dto.setLastModifiedDate(DateFormatUtil.DateFormat(content.getLastModifiedDate()));
        return dto;

    }

}

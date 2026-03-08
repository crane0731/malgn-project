package content.malgn.dto.member;

import content.malgn.utils.DateFormatUtil;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 회원 상세 정보 응답 DTO
 */
@Getter
@Setter
public class MemberInfoResponseDto {

    private String email;//이메일

    private String name;//이름

    private String createdDate;//생성일
    private String lastModifiedDate;//최근 수정일

    private Long contentsCount;//작성한 콘텐츠 수


    /**
     * [생성자]
     * @param email 이메일
     * @param name 이름
     * @param createdDate 생성일
     * @param lastModifiedDate 최근 수정일
     * @param contentsCount 작성한 콘첸츠 수
     */
    public MemberInfoResponseDto(
            String email,
            String name,
            LocalDateTime createdDate,
            LocalDateTime lastModifiedDate,
            Long contentsCount
    ) {
        this.email = email;
        this.name = name;
        this.createdDate = DateFormatUtil.DateFormat(createdDate);
        this.lastModifiedDate = DateFormatUtil.DateFormat(lastModifiedDate);
        this.contentsCount = contentsCount;
    }

}

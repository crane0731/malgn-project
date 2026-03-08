package content.malgn.service.admin;

import content.malgn.domain.Content;
import content.malgn.dto.content.UpdateContentRequestDto;
import content.malgn.service.content.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 관리자 콘텐츠 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminContentService {


    private final ContentService contentService;//콘텐츠 서비스

    /**
     * [서비스 로직]
     * 관리자 콘텐츠 수정
     * @param contentId 콘텐츠 아이디 PK
     * @param dto 수정 요청 DTO
     */
    @Transactional
    public void updateContent(Long contentId,UpdateContentRequestDto dto) {

        //콘텐츠 조회
        Content content = contentService.getById(contentId);

        //업데이트 로직
        content.update(dto.getTitle(), dto.getDescription());
    }
    /**
     * [서비스 로직]
     * 관리자 콘텐츠 삭제
     * @param contentId 콘텐츠 아이디 PK
     */
    @Transactional
    public void deleteContent(Long contentId) {
        //콘텐츠 조회
        Content content = contentService.getById(contentId);

        //논리 삭제 처리
        content.softDelete();
    }


}

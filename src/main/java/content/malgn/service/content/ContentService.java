package content.malgn.service.content;

import content.malgn.domain.Content;
import content.malgn.domain.Member;
import content.malgn.dto.content.CreateContentRequestDto;
import content.malgn.exception.ContentCustomException;
import content.malgn.exception.ErrorMessage;
import content.malgn.repository.ContentRepository;
import content.malgn.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ConcurrentModificationException;

/**
 * 콘텐츠 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentService {

    private final ContentRepository contentRepository;//콘텐츠 레파지토리
    private final MemberService memberService;//회원 서비스



    /**
     * [서비스 로직]
     * 콘텐츠 생성
     * @param dto 콘텐츠 생성 요청 DTO
     */
    @Transactional
    public void createContent(CreateContentRequestDto dto) {

        //현재 로그인한 회원 조회
        Member member = memberService.getLoginMember();

        //콘텐츠 엔티티 생성
        Content content = Content.create(member, dto.getTitle(), dto.getDescription());

        //저장
        contentRepository.save(content);

    }

    /**
     * [서비스 로직]
     * 콘텐츠 삭제 (논리적 삭제)
     * @param contentId 콘텐츠 아이디
     */
    @Transactional
    public void deleteContent(Long contentId) {

        //현재 로그인한 회원 조회
        Member member = memberService.getLoginMember();

        //콘텐츠 조회
        Content content = contentRepository.findById(contentId).orElseThrow(() -> new ConcurrentModificationException(ErrorMessage.NOT_FOUND_CONTENT));

        //해당 콘텐츠가 회원이 작성한 것인지 검증 -> 아니라면 예외 발생
        if(!content.getMember().getId().equals(member.getId())) {
            throw new ContentCustomException(ErrorMessage.NO_PERMISSION);
        }

        //논리적 삭제 처리
        content.softDelete();
    }


    /**
     * 컨텐츠 수정
     */

    /**
     * 컨텐츠 상세 조회
     */

    /**
     * 콘텐츠 목록 조회
     */


}

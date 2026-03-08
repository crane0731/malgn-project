package content.malgn.service.content;

import content.malgn.api.PagedResponse;
import content.malgn.domain.Content;
import content.malgn.domain.Member;
import content.malgn.dto.content.ContentDetailsResponseDto;
import content.malgn.dto.content.CreateContentRequestDto;
import content.malgn.dto.content.SearchContentListCond;
import content.malgn.dto.content.UpdateContentRequestDto;
import content.malgn.exception.ContentCustomException;
import content.malgn.exception.ErrorMessage;
import content.malgn.repository.ContentRepository;
import content.malgn.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ConcurrentModificationException;
import java.util.List;

/**
 * 콘텐츠 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentService {

    private final ContentRepository contentRepository;//콘텐츠 레파지토리
    private final MemberService memberService;//회원 서비스
    private final ContentViewCounterBuffer buffer;//조회수 버퍼

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
        Content content = getById(contentId);

        //해당 콘텐츠가 회원이 작성한 것인지 검증 -> 아니라면 예외 발생
        validateContentOwner(content, member);

        //논리적 삭제 처리
        content.softDelete();
    }


    /**
     * [서비스 로직]
     * 콘텐츠 수정
     * @param contentId 콘텐츠 아이디
     * @param dto 콘텐츠 수정 요청 DTO
     */
    @Transactional
    public void update(Long contentId, UpdateContentRequestDto dto) {

        //현재 로그인한 회원 조회
        Member member = memberService.getLoginMember();

        //콘텐츠 조회
        Content content = getById(contentId);

        //해당 콘텐츠가 회원이 작성한 것인지 검증 -> 아니라면 예외 발생
        validateContentOwner(content, member);

        //수정 로직
        content.update(dto.getTitle(), dto.getDescription());
    }

    /**
     * [서비스 로직]
     * 콘텐츠 상세 조회
     * @param contentId 콘텐츠 아이디 PK
     * @return ContentDetailsResponseDto
     */
    public ContentDetailsResponseDto getContentDetails(Long contentId) {

        //콘텐츠 조회
        Content content = getById(contentId);

        //버퍼에 조회수 누적
        buffer.increase(contentId);

        //응답 DTO 생성 + 반환
        return ContentDetailsResponseDto.create(content);

    }

    /**
     * [서비스 로직]
     * 캐시 전용
     * 무조건 검색 첫페이지 캐싱
     * @return PagedResponse<ContentDetailsResponseDto>
     */
    @Cacheable(
            value = "contentListFirstPage",
            key = "'default'",
            sync = true
    )
    public PagedResponse<ContentDetailsResponseDto> getFirstContents() {


        log.info("캐싱!");

        //비어있는 검색 조건 객체
        SearchContentListCond cond = new SearchContentListCond();

        //페이지된 결과 조회
        Page<Content> pageResult = contentRepository.pageByCond(cond, PageRequest.of(0, 10));

        //응답 dto 변환
        List<ContentDetailsResponseDto> dtoList = pageResult.getContent().stream().map(ContentDetailsResponseDto::create).toList();

        //페이징 전용 응답 DTO 생성 + 반환
        return createPagedResponse(dtoList, pageResult);
    }

    /**
     * [서비스 로직]
     * 콘텐츠 목록 조회 + 검색 조건 + 정렬 + 페이징
     * @param cond 검색 조건 DTO
     * @param page 페이지 번호
     * @return PagedResponse<ContentDetailsResponseDto>
     */
    public PagedResponse<ContentDetailsResponseDto> getContents(SearchContentListCond cond, int page) {

        //페이지된 결과 조회
        Page<Content> pageResult = contentRepository.pageByCond(cond, PageRequest.of(page, 10));

        //응답 dto 변환
        List<ContentDetailsResponseDto> dtoList = pageResult.getContent().stream().map(ContentDetailsResponseDto::create).toList();

        //페이징 전용 응답 DTO 생성 + 반환
        return createPagedResponse(dtoList, pageResult);
    }




    /**
     * [조회]
     * @param contentId 콘텐츠 아이디 PK
     * @return Content
     */
    public Content getById(Long contentId) {
        return contentRepository.findById(contentId).orElseThrow(() -> new ConcurrentModificationException(ErrorMessage.NOT_FOUND_CONTENT));
    }

    //==해당 콘텐츠가 회원이 작성한 것인지 검증 -> 아니라면 예외 발생==//
    private void validateContentOwner(Content content, Member member) {
        if(!content.getMember().getId().equals(member.getId())) {
            throw new ContentCustomException(ErrorMessage.NO_PERMISSION);
        }
    }
    //페이징 전용 응답 DTO 생성 + 반환
    private PagedResponse<ContentDetailsResponseDto> createPagedResponse(List<ContentDetailsResponseDto> dtoList, Page<Content> pageResult) {
        return PagedResponse.<ContentDetailsResponseDto>builder()
                .content(dtoList)
                .page(pageResult.getNumber())
                .size(pageResult.getSize())
                .totalPages(pageResult.getTotalPages())
                .totalElements(pageResult.getTotalElements())
                .first(pageResult.isFirst())
                .last(pageResult.isLast())
                .build();
    }

}

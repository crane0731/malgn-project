package content.malgn.controller.content;

import content.malgn.api.ApiResponse;
import content.malgn.dto.content.CreateContentRequestDto;
import content.malgn.dto.content.UpdateContentRequestDto;
import content.malgn.dto.member.LoginRequestDto;
import content.malgn.service.content.ContentService;
import content.malgn.utils.ErrorCheckUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 콘텐츠 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/malgn/contents")
public class ContentController {

    private final ContentService contentService;//콘텐츠 서비스

    /**
     * [컨틀롤러]
     * 콘텐츠 생성
     * @param requestDto 콘텐츠 생성 요청 DTO
     * @param bindingResult 에러메시지를 바인딩 할 객체
     * @return 성공 메시지 or 에러 메시지
     */
    @PostMapping
    public ResponseEntity<ApiResponse<?>> createContent(@Valid @RequestBody CreateContentRequestDto requestDto, BindingResult bindingResult) {
        // 오류 메시지를 담을 Map
        Map<String, String> errorMessages = new HashMap<>();

        //오류 메시지가 존재하면 이를 반환
        if (ErrorCheckUtil.errorCheck(bindingResult, errorMessages)) {
            return ResponseEntity.badRequest().body(ApiResponse.error("입력값이 올바르지 않습니다.", errorMessages));
        }

        contentService.createContent(requestDto);

        return ResponseEntity.ok(ApiResponse.success(Map.of("message", "콘텐츠 생성 성공")));

    }

    /**
     * [컨트롤러]
     * 콘텐츠 삭제
     * @param contentId 콘텐츠 아이디
     * @return 성공메시지 or 에러메시지
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteContent(@PathVariable("id") Long contentId) {


        contentService.deleteContent(contentId);

        return ResponseEntity.ok(ApiResponse.success(Map.of("message", "콘텐츠 삭제 성공")));

    }
    
    /**
     * [컨트롤러]
     * 콘텐츠 수정
     * @param contentId 콘텐츠 아이디
     * @param requestDto 수정 요청 DTO
     * @param bindingResult 에러메시지를 바인딩 할 객체
     * @return 에러메시지 or 성공 메시지
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateContent(@PathVariable("id") Long contentId , @Valid @RequestBody UpdateContentRequestDto requestDto, BindingResult bindingResult) {

        // 오류 메시지를 담을 Map
        Map<String, String> errorMessages = new HashMap<>();

        //오류 메시지가 존재하면 이를 반환
        if (ErrorCheckUtil.errorCheck(bindingResult, errorMessages)) {
            return ResponseEntity.badRequest().body(ApiResponse.error("입력값이 올바르지 않습니다.", errorMessages));
        }

        contentService.update(contentId, requestDto);

        return ResponseEntity.ok(ApiResponse.success(Map.of("message", "콘텐츠 수정 성공")));

    }


    /**
     * 컨텐츠 상세 조회
     */

    /**
     * 콘텐츠 목록 조회
     */


}

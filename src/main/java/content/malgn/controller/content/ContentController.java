package content.malgn.controller.content;

import content.malgn.api.ApiResponse;
import content.malgn.dto.content.CreateContentRequestDto;
import content.malgn.dto.member.LoginRequestDto;
import content.malgn.service.content.ContentService;
import content.malgn.utils.ErrorCheckUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 컨텐츠 수정
     */

    /**
     * 컨텐츠 삭제
     */

    /**
     * 컨텐츠 상세 조회
     */

    /**
     * 콘텐츠 목록 조회
     */


}

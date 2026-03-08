package content.malgn.controller.admin;

import content.malgn.api.ApiResponse;
import content.malgn.dto.content.UpdateContentRequestDto;
import content.malgn.service.admin.AdminContentService;
import content.malgn.utils.ErrorCheckUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 관리자 콘텐츠 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/malgn/admin/contents")
public class AdminContentController {

    private final AdminContentService adminContentService;//관리자 콘텐츠 서비스


    /**
     * [컨트롤러]
     * 관리자 - 콘텐츠 삭제
     * @param contentId 콘텐츠 아이디
     * @return 성공메시지 or 에러메시지
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> adminDeleteContent(@PathVariable("id") Long contentId) {


        adminContentService.deleteContent(contentId);

        return ResponseEntity.ok(ApiResponse.success(Map.of("message", "콘텐츠 삭제 성공")));

    }

    /**
     * [컨트롤러]
     * 관리자 - 콘텐츠 수정
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

        adminContentService.updateContent(contentId, requestDto);

        return ResponseEntity.ok(ApiResponse.success(Map.of("message", "콘텐츠 수정 성공")));

    }

}

package content.malgn.controller.member;

import content.malgn.api.ApiResponse;
import content.malgn.dto.member.SignUpRequestDto;
import content.malgn.service.member.MemberService;
import content.malgn.utils.ErrorCheckUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 회원 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/malgn/auth")
public class LoginController {

    private final MemberService memberService;//회원 서비스


    /**
     * [컨트롤러]
     * 회원 가입
     * @param requestDto 회원 가입 요청 DTO
     * @param bindingResult 에러메시지를 바인딩할 객체
     * @return 성공 메시지 or 에러 메시지
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<?>>signUp(@Valid @RequestBody SignUpRequestDto requestDto, BindingResult bindingResult) {

        // 오류 메시지를 담을 Map
        Map<String, String> errorMessages = new HashMap<>();

        //오류 메시지가 존재하면 이를 반환
        if (ErrorCheckUtil.errorCheck(bindingResult, errorMessages)) {
            return ResponseEntity.badRequest().body(ApiResponse.error("입력값이 올바르지 않습니다.", errorMessages));
        }

        memberService.signup(requestDto);
        return ResponseEntity.ok(ApiResponse.success(Map.of("message", "회원가입 성공")));

    }

    /**
     * 로그인
     */

    /**
     * 로그 아웃
     */
}

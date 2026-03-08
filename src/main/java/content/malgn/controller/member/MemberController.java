package content.malgn.controller.member;

import content.malgn.api.ApiResponse;
import content.malgn.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 회원 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/malgn/members")
public class MemberController {

    private final MemberService memberService;//회원 서비스
    

    /**
     * [컨트롤러]
     * 회원 자신의 정보 조회
     * @return  MemberInfoResponseDto
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<?>>getMyInfo(){
        return ResponseEntity.ok(ApiResponse.success(memberService.getMyInfo()));

    }

    /**
     * 회원 탈퇴
     */
}

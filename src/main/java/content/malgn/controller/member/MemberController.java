package content.malgn.controller.member;

import content.malgn.api.ApiResponse;
import content.malgn.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * [컨트롤러]
     * 회원 탈퇴
     * @return 성공 메시지 or 에러 메시지
     */
    @PostMapping("/me/withdraw")
    public ResponseEntity<ApiResponse<?>>withdrawMember(){
        memberService.withdraw();
        return ResponseEntity.ok(ApiResponse.success(Map.of("message", "회원 탈퇴 성공")));

    }
}

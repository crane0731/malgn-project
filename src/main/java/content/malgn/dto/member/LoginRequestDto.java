package content.malgn.dto.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * 로그인 요청 DTO
 */
@Getter
public class LoginRequestDto {
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "패스워드를 입력해주세요.")
    private String password;
}

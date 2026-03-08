package content.malgn.dto.content;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * 콘텐츠 생성 요청 DTO
 */
@Getter
public class CreateContentRequestDto {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    private String description;

}

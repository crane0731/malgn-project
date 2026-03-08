package content.malgn.dto.content;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 콘텐츠 업데이트 요청 DTO
 */
@Getter
@Setter
public class UpdateContentRequestDto {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    private String description;
}

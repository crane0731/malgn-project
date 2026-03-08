package content.malgn.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * API 응답 형식을 맞추기 위한 DTO
 */
@Getter
@Setter
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success; //성공 여부
    private String message; //메시지(성공 or 오류)
    private T data; //실제 응답 데이터

    /**
     * api 요청에 성공했을 때
     * @param data 응답 데이터
     * @return
     * @param <T>
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true,"요청이 성공했습니다.",data);
    }

    /**
     * api 요청에 실패했을 때
     * @param message
     * @return
     * @param <T>
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false,message,null);
    }

    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>(false, message, data);
    }
}

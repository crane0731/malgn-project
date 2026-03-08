package content.malgn.exception;
/**
 * 커스텀 에러 메시지
 */
public final class ErrorMessage {

    /**
     * 공용
     */
    public static final String NO_PERMISSION = "권한이 없습니다.";

    /**
     * 회원
     */
    public static final String NOT_FOUND_MEMBER = "회원을 찾을 수 없습니다.";
    public static final String NOT_FOUND_REFRESH_TOKEN = "리프레쉬 토큰을 찾을 수 없습니다.";
    public static final String INVALID_TOKEN = "유효하지 않은 토큰입니다.";
    public static final String DUPLICATED_EMAIL = "이메일이 이미 존재합니다.";
    public static final String PASSWORD_MISMATCH = "비밀번호가 일치하지 않습니다.";
    public static final String EXPIRED_TOKEN = "토큰이 만료되었습니다.";
    public static final String ALREADY_LOGOUT_MEMBER = "이미 로그아웃된 회원입니다.";

}

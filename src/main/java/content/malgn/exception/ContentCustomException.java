package content.malgn.exception;

/**
 * 콘텐츠 커스텀 예외
 */
public class ContentCustomException extends RuntimeException {
    public ContentCustomException(String message) {
        super(message);
    }
}

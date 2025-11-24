package egovframework.lab.exception;

public abstract class ExternalApiException extends RuntimeException {
    public ExternalApiException(String message) {
        super(message);
    }
    public ExternalApiException(String message, Throwable cause) {
        super(message, cause);
    }
}

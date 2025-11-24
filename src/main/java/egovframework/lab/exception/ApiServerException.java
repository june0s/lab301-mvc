package egovframework.lab.exception;

public class ApiServerException extends ExternalApiException {
    private final int statusCode;

    public ApiServerException(int statusCode, String message) {
        super("Server Error [" + statusCode + "]: " + message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

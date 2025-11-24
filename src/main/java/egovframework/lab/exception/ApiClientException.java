package egovframework.lab.exception;

public class ApiClientException extends ExternalApiException {
    private final int statusCode;

    public ApiClientException(int statusCode, String message) {
        super("Client Error [" + statusCode + "]: " + message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

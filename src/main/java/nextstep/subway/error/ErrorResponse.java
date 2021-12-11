package nextstep.subway.error;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

    private final int statusCode;
    private final String message;

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus toHttpStatus() {
        return HttpStatus.valueOf(statusCode);
    }
}
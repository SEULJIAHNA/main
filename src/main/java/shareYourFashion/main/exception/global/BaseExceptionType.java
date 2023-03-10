package shareYourFashion.main.exception.global;

import org.springframework.http.HttpStatus;

public interface BaseExceptionType {
    int getErrorCode();

    HttpStatus getHttpStatus();

    String getErrorMessage();
}

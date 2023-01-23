package shareYourFashion.main.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shareYourFashion.main.error.ErrorResponse;
import shareYourFashion.main.exception.DoNotFoundImageObjectException;
import shareYourFashion.main.exception.DoNotFoundUserEntityException;
import shareYourFashion.main.exception.PasswordMismatchException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

@RestControllerAdvice // 모든 exception 발생 시 위 클래스로 매핑해줌
public class GlobalRestExceptionHandler {

    private final static Logger log = Logger.getGlobal();

    //사용자 비밀번호 불일치 시 발생 하는 예외
    @ExceptionHandler(PasswordMismatchException.class)
//    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "비밀번호가 일치하지 않습니다.")
    public ResponseEntity<Error> passwordMismatch(PasswordMismatchException pme){
        System.out.println("GlobalExceptionHandler : passwordMismatch : execute");
        // 예외 발생 시 클라이언트에 넘겨줄 에러 객체 생성

        Error error = new Error(pme.getMessage() , pme);

        // response header class 생성
        HttpHeaders headers = new HttpHeaders();

        // 응답할 responseHeader 세팅


        return new ResponseEntity<> (error , headers , HttpStatus.BAD_REQUEST );
    }


    /* user service logic 수행 중 user entity를 못찾을 경우 발생하는 exception */
    @ExceptionHandler(DoNotFoundUserEntityException.class)
//    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "오류 : 해당 유저 엔터티를 찾을 수 없습니다 ")
    public ResponseEntity<Error> DoNotFoundUserEntity (DoNotFoundUserEntityException due) {
        System.out.println("GlobalExceptionHandler : doNotFoundUserEntity : execute");

        log.severe("doNotFoundUserEntity");
        // 예외 발생 시 클라이언트에 넘겨줄 에러 객체 생성

        Error error = new Error(due.getMessage(), due);

        // response header class 생성
        HttpHeaders headers = new HttpHeaders();

        // 응답할 response Header 세팅
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(error, headers, HttpStatus.NOT_FOUND);
    }

    /* user service logic 수행 중 user entity를 못찾을 경우 발생하는 exception */
    @ExceptionHandler(DoNotFoundImageObjectException.class)
//    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "오류 : 해당 유저 엔터티를 찾을 수 없습니다 ")
    public ResponseEntity<Error> doNotFoundImageObject (DoNotFoundImageObjectException due) {
        System.out.println("GlobalExceptionHandler : DoNotFoundImageObjectException : execute");

        Error error = new Error(due.getMessage(), due);

        // response header class 생성
        HttpHeaders headers = new HttpHeaders();

        // 응답할 response Header 세팅
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(error, headers, HttpStatus.NOT_FOUND);
    }

    // fallback method
    @ExceptionHandler(Exception.class) // exception handled
    public ResponseEntity<ErrorResponse> handleExceptions(
            Exception e
    ) {
        // ... potential custom logic

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500

        // converting the stack trace to String
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage(),
                        stackTrace // specifying the stack trace in case of 500s
                ),
                status
        );
    }
}

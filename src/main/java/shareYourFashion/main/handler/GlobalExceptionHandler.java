package shareYourFashion.main.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shareYourFashion.main.error.ErrorResponse;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger log = Logger.getGlobal();


    /*
     *  유저 인증(authentication) 오류 및 실패 예외 시 발생
     * */

    @ExceptionHandler(value = {UserAuthenticationExceptionHandler.class })
    public String handleCustomUserAuthenticationException (final UserAuthenticationExceptionHandler e ,
                                                                                  final Model model ){
        log.severe("유저 인증(authentication) 예외발생");
        // 예외 발생 시 클라이언트에 넘겨줄 에러 객체 생성

        HttpStatus status = HttpStatus.NOT_FOUND; // 404 error code

        model.addAttribute("errorCode" , status.value());
        model.addAttribute("errorMessage" , e.getMessage());

        return "/error/userAuthenticationErrorPage";
    }

}

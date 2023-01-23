package shareYourFashion.main.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("userAuthenticationExceptionHandler")
public class UserAuthenticationExceptionHandler extends Exception {
    public UserAuthenticationExceptionHandler() {
        super();
    }

    public UserAuthenticationExceptionHandler(String message) {
        super(message);
    }

    public UserAuthenticationExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAuthenticationExceptionHandler(Throwable cause) {
        super(cause);
    }
}

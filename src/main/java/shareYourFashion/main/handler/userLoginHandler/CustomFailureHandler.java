package shareYourFashion.main.handler.userLoginHandler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("customLoginFailHandler")
public class CustomFailureHandler implements AuthenticationFailureHandler {

    private static final String REDIRECT_URL = "/login?error=1";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        System.out.println("(login error) exception type : " + exception.getClass().getName());
        System.out.println("(login error) exception message : " + exception.getMessage());

        response.sendRedirect(REDIRECT_URL);
    }
}

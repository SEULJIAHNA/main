package shareYourFashion.main.handler.userLoginHandler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import shareYourFashion.main.config.auth.PrincipalDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@Component("customLoginSuccessHandler")
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private static final String REDIRECT_URL = "/";
    private static final Logger LOG = Logger.getGlobal();

    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);

    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        PrincipalDetails principalDetails  = (PrincipalDetails) authentication.getPrincipal();
        LOG.info("로그인 유저정보 : " + principalDetails.toString());

        // 로그인 성공시 리다이렉트 메소드 실행
        defaultRedirectStrategy(request , response , authentication);


    }

    protected void defaultRedirectStrategy(HttpServletRequest request, HttpServletResponse response,
                                           Authentication authentication) throws IOException, ServletException {

        // requestCache 객체를 통해 SavedRequest 객체 생성
        SavedRequest savedRequest = requestCache.getRequest(request , response); // 세션에 저장된 DefaultSavedRequest 를 가져옴
        if(savedRequest != null ) {
            // not null 이므로 이전 인가 받기 전에 주소 정보 (url)이 존재 -> 로그인 전 urㅣ로 redirect
           String targetUrl = savedRequest.getRedirectUrl();
           redirectStrategy.sendRedirect(request , response , targetUrl); // 이전의 주소정보를 얻어와서 리다이렉트 시킴
        } else {
            // 세션에 DefaultSavedRequest 객체가 존재하지 않기 때문에 인증 실패 이전의 주소로 이동한다던지 하는 작업들이 일어나지 않습니다.
            redirectStrategy.sendRedirect(request , response , REDIRECT_URL);
        }

    }
}

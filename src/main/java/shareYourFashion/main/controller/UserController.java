package shareYourFashion.main.controller;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import shareYourFashion.main.config.auth.PrincipalDetails;
import shareYourFashion.main.dto.UserFormDTO;
import shareYourFashion.main.handler.UserAuthenticationExceptionHandler;
import shareYourFashion.main.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Objects;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    private static Logger logger = Logger.getLogger(UserController.class.getName());


    /*
    *  url collections
    * */
    private final String USER_AUTHENTICATION_ERROR_PAGE_URL = "/users/authentication/error";
    private final String LOGIN_PAGE_URL = "/login";
    private final String SIGNUP_PAGE_URL = "/new";
    private final String MY_PAGE_URL = "/users/{nickname}";
    private final String LOGIN_PAGE_HTML = "auth/login";
    private final String SIGNUP_PAGE_HTML = "auth/signUp";
    private final String MY_PAGE_HTML = "pc/user/myPage";


    /*
    *  login page 요청 (get)
    * */
    @GetMapping(value = LOGIN_PAGE_URL)
    public String login(@RequestParam(required = false) String error , Model model) {

        // error parameter가 넘어온 경우(로그인 처리 중 에러 발생) error 메세지 검증
        if(StringUtils.hasText(error)) {
            model.addAttribute("error" , "아이디 또는 패스워드를 확인해주세요. ");

        }

        return LOGIN_PAGE_HTML;
    }


    /*
     *  회원가입 페이지 요청 (get)
     * */
    @GetMapping(value = SIGNUP_PAGE_URL)
    public String join(Model model){

        UserFormDTO userInfo = new UserFormDTO();

        model.addAttribute("userJoinForm" , new UserFormDTO());


        return SIGNUP_PAGE_HTML;
    }


    /*
    *  유저 인증 에러 페이지 요청
    * */

    @GetMapping(value = USER_AUTHENTICATION_ERROR_PAGE_URL)
    public String error(){
        return "error/userAuthenticationErrorPage";
    }


    /*
     * */
    @GetMapping(value = MY_PAGE_URL)
    public String myPage(@PathVariable("nickname") String nickname ,
                                                 Authentication authentication , Model model) throws UserAuthenticationExceptionHandler {

        PrincipalDetails userDetails;
        System.out.println("authentication = " + authentication);
        if(Objects.isNull(authentication)) { // authentication 객체가 null 인경우 예외 발생
            logger.info("user authentication is null");

            throw new UserAuthenticationExceptionHandler();

        } else {
            //현재 로그인한 유저의 정보를 받아옵니다.
            userDetails = (PrincipalDetails) authentication.getPrincipal();

            if(!userDetails.getNickname().equals(nickname)) {
                logger.info(" 현재 로그인된 유저 닉네임과 요청한 my page 유저 닉네임과 불일치 ");
                System.out.println("userDetails = " + userDetails.getNickname());
                System.out.println("nickname = " + nickname);

                throw new UserAuthenticationExceptionHandler("현재 로그인된 유저 정보와 요청하신 유저 페이지 정보와 일치하지 않습니다.");

            } else if (!userDetails.isAccountNonExpired()) {
                logger.info(" 현재 로그인 중인 계정 유효성 만료  ");
                throw new UserAuthenticationExceptionHandler();
            }

        }

        model.addAttribute("principal" , authentication.getPrincipal());

        return "pc/user/myPage";
    }
}

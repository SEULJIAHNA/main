package shareYourFashion.main.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shareYourFashion.main.dto.UserFormDTO;
import shareYourFashion.main.service.UserService;

import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor

public class UserApiController {

    private static Logger logger = Logger.getLogger(UserApiController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encode;

    /*
    *  request URL COLLECTIONS
    * */



    @GetMapping("/user-emails/{email}/exists")
    public ResponseEntity<Boolean> checkEmailDuplicate(@PathVariable String email) {
        System.out.println(ResponseEntity.ok(userService.checkEmailDuplicate(email)));
        return ResponseEntity.ok(userService.checkEmailDuplicate(email));
    }



    @GetMapping("/user-nicknames/{nickname}/exists")
    public ResponseEntity<Boolean> checkNicknameDuplicate(@PathVariable String nickname) {
        return ResponseEntity.ok(userService.checkNicknameDuplicate(nickname));
    }


    /*
     * 회원가입을 위한 dto class가 넘어왔을때
     * 실패 : 실패 메세지와 함께 url 변동 x
     * 성공 : 회원가입 성공 시 login page호 301 permanently redirect (get)
     */

    @PostMapping("/new")
    public ResponseEntity<?> save(@RequestBody @Validated UserFormDTO userDto , Errors errors , Model model , UriComponentsBuilder b) throws Exception {
        System.out.println("UserController: save 호출됨");

        HttpHeaders headers = new HttpHeaders();
        UriComponents uriComponents;

        if (errors.hasErrors()) {
            /* 회원가입 실패시 기존 입력 데이터 유지 */
            model.addAttribute("userJoinForm", userDto);

            /* 유효성 통과 못한 필드와 메시지를 핸들링 */
            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            /*회원가입 페이지로 다시 리턴 */
            uriComponents = b.path("/users/new").build();
            headers.setLocation(uriComponents.toUri());

            return new ResponseEntity<Void>(headers , HttpStatus.OK);
        }

        // 회원가입 필드 값 검증 후 db에 저장
        String rawPassword = userDto.getPassword(); // 해쉬 암호화 전 비밀번호
        String encPassword = encode.encode(rawPassword); // 해쉬 암호화 된 비밀번호

        System.out.println("rawPassword = " + rawPassword);
        System.out.println("encPassword = " + encPassword);

        userDto.setPassword(encPassword);

        userService.userJoin(userDto);

        /*회원가입 성공시 login 페이지로 리다이렉트*/
        uriComponents = b.path("/login").build();
        headers.setLocation(uriComponents.toUri());
        return new ResponseEntity<Void>(headers , HttpStatus.OK);
    }




}

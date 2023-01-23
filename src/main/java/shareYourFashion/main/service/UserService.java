package shareYourFashion.main.service;


import org.springframework.validation.Errors;
import shareYourFashion.main.domain.User;
import shareYourFashion.main.dto.UserFormDTO;

import java.util.Map;

public interface UserService {
    /*
    *  Email , Nickname 중복확인
    * */

    Boolean checkEmailDuplicate(String email);
    Boolean checkNicknameDuplicate(String nickname);

    /*
    *  userJoinForm 필드 데이터 유효성 검증
    * */

    Map<String , String> validateHandling(Errors error);

    /*
    *  회원가입 로직
    *  @Param : userJoinForm DTO
    * */
    void userJoin(UserFormDTO userDto) throws Exception;


    /*
    * converts userRequestDto to user Entity
    * */

    User userDtoToEntity(UserFormDTO userDto);

    void validateDuplicateMember(User user);


    boolean existsById(Long userId);

    User findByEmail(String email);
//    User login(LoginForm loginform);

    User findById(Long userId);
}

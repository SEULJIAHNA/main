package shareYourFashion.main.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import shareYourFashion.main.domain.User;
import shareYourFashion.main.dto.UserFormDTO;
import shareYourFashion.main.exception.DoNotFoundUserEntityException;
import shareYourFashion.main.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findByEmail(String email) {

        User user = null;

        try{
            user = userRepository.findByEmail(email).get();
        } catch(Exception e) {
            System.out.println("e.getMessage() = " + e.getMessage());
            e.printStackTrace();
        }

        return user;
    }


    /**
     * (회원가입) email , nickname 중복 확인
     *
     * @param  : email , nickname
     * @return Boolean (있으면 true , 없으면 fasle)
     */
    @Override
    public Boolean checkEmailDuplicate(String email) {

        return userRepository.existsByEmail(email);
    }

    @Override
    public Boolean checkNicknameDuplicate(String nickname) {

        return userRepository.existsByNickname(nickname);

    }

    /* 회원가입 시, 유효성 체크 */
    @Override
    @Transactional(readOnly = true)
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        /* 유효성 검사에 실패한 필드 목록을 받음 */
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }


    /*
    *  convert userRequestDto to user Entity
    * */

    @Override
    public User userDtoToEntity(UserFormDTO userDto) {
        User userEntity = User.create(userDto);


        return userEntity;
    }

    /*
    *  유저 회원가입 (db에 등록)
    * */

    @Override
    @Transactional
    public void userJoin(UserFormDTO userDto) throws Exception {

        // userRequestDto to userEntity 전환
        User user = this.userDtoToEntity(userDto);

        /*user 중복 확인*/


        // userEntity 암호화
        User userEntity = user.hashPassword(passwordEncoder);

        if(Objects.isNull(userEntity)) {
            throw new DoNotFoundUserEntityException("해당 유저 엔티티가 존재하지 않습니다.");
        }

        // 회원가입 시 default profile image 생성 후 user entity에 등록


        userRepository.save(userEntity);
    }


    /*유저 중복 확인*/
    @Override
    public void validateDuplicateMember(User user) {
        if(this.checkEmailDuplicate(user.getEmail())) {

        }
    }
    @Override
    public boolean existsById(Long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public User findById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        user.orElseThrow(()-> {throw new UsernameNotFoundException("해당 아이디를 가진 유저가 존재하지 않습니다.");});

        return user.get();
    }
}



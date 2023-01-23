package shareYourFashion.main.userApiTest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import shareYourFashion.main.constant.Roles;
import shareYourFashion.main.domain.User;
import shareYourFashion.main.dto.UserFormDTO;
import shareYourFashion.main.constant.JoinPath;
import shareYourFashion.main.constant.Sex;
import shareYourFashion.main.repository.UserRepository;
import shareYourFashion.main.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional(readOnly = true)

class RegisterUserToDataBaseTest {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private UserService userService;


    @Test
    @Transactional
    public void 유저_가입_테스트() throws Exception{
        //given
        UserFormDTO userDto = new UserFormDTO().builder()
                .user_role(Roles.USER).age("10").email("agh0314@naver.com")
                .sex(Sex.MAN).nickname("안광현").password("agh@p970314").joinPath(JoinPath.DEFAULT_ACCOUNT).build();
        //when
        userService.userJoin(userDto);

        //then
        Optional<User> findUser = userRepository.findByEmail(userDto.getEmail());
        findUser.orElseThrow(()-> new Exception("user Entity 찾는 중 오류 발생"));

        Assertions.assertThat(findUser.get().getEmail()).isEqualTo(userDto.getEmail());



    }
}

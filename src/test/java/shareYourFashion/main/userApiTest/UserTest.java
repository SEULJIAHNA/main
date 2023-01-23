package shareYourFashion.main.userApiTest;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import shareYourFashion.main.config.SecurityConfig;
import shareYourFashion.main.constant.Roles;
import shareYourFashion.main.domain.User;
import shareYourFashion.main.constant.JoinPath;
import shareYourFashion.main.constant.Sex;
import shareYourFashion.main.repository.UserRepository;
import shareYourFashion.main.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional(readOnly = true)

class UserTest {

    @Autowired
    private SecurityConfig securityConfig;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;


    @Test
    @Transactional
    public void 유저_패스워드_암호화_테슽() throws Exception{
        //given
        User user = new User().builder()
                .roles(Roles.USER).age("10").email("agh0314@naver.com")
                .sex(Sex.MAN).nickname("안광현").password("agh@p970314").joinPath(JoinPath.DEFAULT_ACCOUNT).build();


        //when 
        User testUser = user.hashPassword(passwordEncoder);

        em.persist(testUser);
        em.flush();
        
        //then


        System.out.println(securityConfig.passwordEncoder());

    }
}

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
import shareYourFashion.main.constant.JoinPath;
import shareYourFashion.main.repository.UserRepository;
import shareYourFashion.main.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional(readOnly = true)
class UserDuplicateTesting {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private  UserService userService;

    @Test
    @Transactional
    public void 유저_이메일_닉네임_중복테스트() throws Exception{
        //given
        User user = User.builder().age("24").roles(Roles.USER)
                .email("agh0314@naver.com").nickname("ahn").password("21312321")
                .joinPath(JoinPath.KAKAO_ACCOUNT)
                .build();

        em.persist(user);
        em.flush();

        //when
        Boolean isDuplicatedEmail = userService.checkEmailDuplicate(user.getEmail());
        Boolean isDuplicatedNickname = userService.checkNicknameDuplicate(user.getNickname());

        // 없는 이메일 닉네임 넣어보기
        Boolean isNotDuplicatedEmail = userService.checkEmailDuplicate("agh0316@nave.com");
        Boolean isNotDuplicatedNickname = userService.checkNicknameDuplicate("ghfnfnfnf");

        //then
        Assertions.assertThat(isDuplicatedEmail).isEqualTo(true);
        Assertions.assertThat(isDuplicatedNickname).isEqualTo(true);

        Assertions.assertThat(isNotDuplicatedEmail).isEqualTo(false);
        Assertions.assertThat(isNotDuplicatedNickname).isEqualTo(false);

    }
}

package shareYourFashion.main.testCase;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import shareYourFashion.main.constant.Roles;
import shareYourFashion.main.domain.Board;
import shareYourFashion.main.domain.User;
import shareYourFashion.main.constant.JoinPath;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional(readOnly = true)
class Testing {

    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    public void 조인테이블생기는이유검증() throws Exception{
        //given
        User user = User.builder().age("24").roles(Roles.USER)
                .email("agh0314@naver.com").nickname("ahn").password("21312321")
                .joinPath(JoinPath.KAKAO_ACCOUNT)
                .build();

        Board board = Board.builder().author(user).content("dwq").title("dwqdwqdq")
                .author(user).build();

        //when

        em.persist(user);
        em.persist(board);
        em.flush();
        //then
    }
}

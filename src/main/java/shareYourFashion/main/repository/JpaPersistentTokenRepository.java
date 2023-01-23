package shareYourFashion.main.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import shareYourFashion.main.domain.PersistentLogin;

import java.util.Date;

// PersistentTokenRepository method 4개 구현 (필수)
@Repository
@Primary
public class JpaPersistentTokenRepository implements PersistentTokenRepository {

    private final PersistentLoginRepository repository;


    public JpaPersistentTokenRepository(final PersistentLoginRepository repository) {
        this.repository = repository;
    }

    // 새로운 remember-me 쿠키를 발금할 때 담을 토큰을 생성하기 위한 메서드
    @Override
    public void createNewToken(final PersistentRememberMeToken token) {
        repository.save(PersistentLogin.from(token));
    }


    // 토큰 변경 메소드
    @Override
    public void updateToken(final String series,  final String tokenValue, final Date lastUsed) {
        repository.findBySeries(series)
                .ifPresent(persistentLogin -> {
                    persistentLogin.updateToken(tokenValue , lastUsed);
                    repository.save(persistentLogin);
                });
    }

    // 사용자에게서 remember-me 쿠키를 이용한 인증 요청이 들어올 경우 호출될 메서드
    // 사용자가 보내온 쿠키에 담긴 시리즈로 데이터베이스를 검색해 토큰을 찾음
    @Override
    public PersistentRememberMeToken getTokenForSeries(final String seriesId) {
        return repository.findBySeries(seriesId)
                .map(persistentLogin ->
                    new PersistentRememberMeToken(
                            persistentLogin.getUsername(),
                            persistentLogin.getSeries(),
                            persistentLogin.getToken(),
                            persistentLogin.getLastUsed()
                    ))
                .orElseThrow(IllegalStateException::new);
    }


    // 세션이 종료될 경우 데이터베이스에서 영구 토큰 제거
    @Override
    public void removeUserTokens(String username) {
        repository.deleteAllInBatch(repository.findByUsername(username));
    }
}

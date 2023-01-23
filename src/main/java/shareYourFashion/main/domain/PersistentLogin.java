package shareYourFashion.main.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Entity
@Table(name = "persistent_logins")
public class PersistentLogin implements Serializable {

    @Id
    private String series;

    @Column(nullable= false)
    private String token;

    @Column(nullable= false)
    private Date lastUsed;

    @Column(nullable= false)
    private String username;

    // JPA의 정책상 무조건 기본 생성자 필요 (private 불가)
    protected PersistentLogin() {}

    // 생성자를 외부에 노출하지 않습니다.
    private PersistentLogin(final PersistentRememberMeToken token) {
        this.series = token.getSeries();
        this.username = token.getUsername();
        System.out.println(" username  = " + token.getUsername() );
        this.token = token.getTokenValue();
        this.lastUsed = token.getDate();
    }

    // 정적 팩토리 메서드
    public static PersistentLogin from(final PersistentRememberMeToken token) {
        return new PersistentLogin(token);
    }

    // 토큰 업데이트
    public void updateToken(final String tokenValue , final Date lastUsed) {
        this.token = tokenValue;
        this.lastUsed = lastUsed;
    }

}

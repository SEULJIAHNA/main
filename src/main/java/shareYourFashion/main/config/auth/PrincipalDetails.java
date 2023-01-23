package shareYourFashion.main.config.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import shareYourFashion.main.constant.Roles;
import shareYourFashion.main.domain.User;
import shareYourFashion.main.domain.UserProfileImage;
import shareYourFashion.main.domain.valueTypeClass.Image;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@NoArgsConstructor
@Component
public class PrincipalDetails implements UserDetails {

    private User user; //접근 주체(Principal) : 보호된 리소스에 접근하는 대상

    public PrincipalDetails(User user){
        this.user = user;
    }

    // 사용자의 권한을 콜렉션 형태로 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();

        collectors.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRoles().name();
            }
        });

        return collectors;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getNickname() {
        return user.getNickname();
    }

    public Roles getRoles() {
        return user.getRoles();
    }


    public String getUserProfileImageName() {
        return user.getProfileImage().getImage().getFileName();
    }

    public String getUserBackgroundImageName() {
        return user.getBackgroundImage().getImage().getFileName();
    }

    public String getActivityCount() {
        return null;
    }

    // 계정 만료 여부 반환
    // true -> 만료되지 않았음
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // true -> 잠금되지 않았음
        return true;
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true; // true -> 잠금되지 않았음
    }

    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true; // true -> 사용 가능
    }

    @Override
    public String toString() {
        return "PrincipalDetails{" +
                "user=" + user +
                '}';
    }


}

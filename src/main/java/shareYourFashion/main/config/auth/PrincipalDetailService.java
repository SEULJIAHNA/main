package shareYourFashion.main.config.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shareYourFashion.main.domain.User;
import shareYourFashion.main.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    /**
     * Spring Security 필수 메소드 구현
     *
     * @param email 이메일
     * @return UserDetails
     * @throws UsernameNotFoundException 유저가 없을 때 예외 발생
     */

    // 스프링 시큐리티 내부 에서 자동으로 비밀번호 디코딩 후 체킹해줌 즉 아이디 체킹 로직만 구현하면됨
    @Override /// 기본적인 반환 타입은 UserDetails, UserDetails를 상속받은 principalDetail로 반환 타입 지정 (자동 다운 캐스팅)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(email);

        user.orElseThrow(() -> {
            throw new UsernameNotFoundException("userEmail : " + email + "not Found!");
        });


        return new PrincipalDetails(user.get());
    }


}

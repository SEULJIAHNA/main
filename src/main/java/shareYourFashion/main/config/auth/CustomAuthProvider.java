package shareYourFashion.main.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@Component
public class CustomAuthProvider implements AuthenticationProvider {

    @Autowired
    private PrincipalDetailService principalDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static Logger logger = Logger.getLogger(CustomAuthProvider.class.getName());


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String id = authentication.getName();
        String password = (String) authentication.getCredentials(); // Authentication.getCredentials()는 사용자 인증에 이용하는 정보(일반적이라면 로그인 비밀번호)를 반환한다.
        System.out.println("authentication = " + authentication);
        logger.info("current login user email : " + id  + "  current login user password : " + password);

        PrincipalDetails user = (PrincipalDetails) principalDetailService.loadUserByUsername(id);

        if(!passwordEncoder.matches(password , user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 맞지않습니다.");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRoles().getSecurityRole()));

        //사용자의 아이디로 DB에서 정보를 조회한다. 그리고 입력받은 비밀번호와 디비에 저장된 비밀번호(BCrypt 방식으로 해싱된)가맞지않으면 BadCredentialsException을 발생시킨다.
        //DB에서 얻어온 사용자의 권한을 부여한다. 이때 권한명에 'ROLE_'를 붙혀주자. 이건 시큐리티에서 default prefix로 정의해둔 권한값이다.
        //그리고 사용자의 정보로 UsernamePasswordAuthenticationToken을 생성한뒤 인증이 완료된 Authentication을 리턴한다.

        return new UsernamePasswordAuthenticationToken(user , password , authorities);
    }

    //supports() 메서드는 인자로 Class< ? > authentication을 받지만 실제로는 Class< ? extends Authentication> 이다.
    //즉, supports() 메서드가 authenticate() 메서드로 전달되는지에 대한 여부만 확인한다.
    //UsernamePasswordAuthenticationToken 클래스는 Authentication 인터페이스의 구현체이다.
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

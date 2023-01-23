package shareYourFashion.main.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import shareYourFashion.main.config.auth.PrincipalDetailService;
import shareYourFashion.main.constant.Roles;
import shareYourFashion.main.repository.JpaPersistentTokenRepository;
import shareYourFashion.main.repository.PersistentLoginRepository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

// 빈 등록 : 스프링 컨테이너에서 객체를 관리
@Configuration(proxyBeanMethods = false) // ioc 관리
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableWebSecurity // 시큐리티 필터 등록 설정 가능케 함
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크
@RequiredArgsConstructor
public class SecurityConfig{

    /*url 모음*/
    private static final String MAIN_PATH = "/**";
    private static final String ADMIN_PATH = "/admin/**";
    private static final String USER_PATH = "/users/**";
    private static final String IGNORE_FAVICON = "/favicon.ico";
    private static final String LOGIN_PAGE = "/login";
    private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String LOGOUT_URL = "/logout";
    private static final String LOGOUT_SUCCESS_URL = "/";
    private static final String MAIN_PAGE = "/";
    private static final String REMEMBER_ME_TOKEN_KEY = "uniqueKey";
    private static final String DEFUALT_REMEMBER_ME_NAME = "remember-me";
    private static final int REMEMBER_ME_TOKEN_EXPIRED = 86400 * 14;

    @Autowired
    @Qualifier("customLoginSuccessHandler")
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    @Qualifier("customLoginFailHandler")
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    @Qualifier("customLogoutSuccessHandler")
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private PersistentTokenRepository tokenRepository;

    private final PrincipalDetailService principalDetailService;

    private final DataSource dataSource;

    /**
     * PasswordEncoder를 Bean으로 등록
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        String idForEncode = "bcrypt";
        Map encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }

    //AuthenticationManager 를 노출시키고 싶으면
    //WebSecurituConfigurerAdapter 를 상속하는 Security Config 클래스를 만들고
    //WebSecurityConfigurerAdapter 의 authenticationManagerBean 를 오버라이드 하고
    //bean 으로 등록 후에 사용 해야 한다고 한다.
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    // 시큐리티가 대신 로그인해주는데 password를 가로채기를 하는데
    // 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
    // 같은 해쉬로 암호화해서 db에 있는 해쉬랑 비교할 수 있음


    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable(); // csrf 토큰 비활성화 , spring security는 csrf토큰이 없는 요청은 자동으로 막아버림(ex ajax 통신)

        http
            .authorizeRequests()
            .antMatchers(ADMIN_PATH).hasRole(Roles.ADMIN.name())
            .anyRequest().permitAll();  // /auth 경로가 아닌 모든 주소 인증 필요


        http  // 자동로그인 관리
            .rememberMe()
            .rememberMeParameter(DEFUALT_REMEMBER_ME_NAME) // default: remember-me, checkbox 등의 이름과 맞춰야 함
            .key(REMEMBER_ME_TOKEN_KEY)
            .tokenValiditySeconds(REMEMBER_ME_TOKEN_EXPIRED)
            .alwaysRemember(false) // 사용자가 체크박스를 활성화하지 않아도 항상 실행, default: false
            .userDetailsService(principalDetailService) // 기능을 사용할 때 사용자 정보가 필요함. 반드시 이 설정 필요함.
        .and()
            .formLogin()
            .usernameParameter("email")
            .passwordParameter("password")
            .loginPage(LOGIN_PAGE)
            .loginProcessingUrl(LOGIN_PROCESSING_URL)
            .successHandler(authenticationSuccessHandler)
            .failureHandler(authenticationFailureHandler)
        .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_URL))
            .logoutSuccessUrl(LOGOUT_SUCCESS_URL)
            .invalidateHttpSession(Boolean.TRUE)
            .deleteCookies("JSESSIONID" , "remember-me")
            .logoutSuccessHandler(logoutSuccessHandler); // 로그아웃 성공 후 핸들러



        /* RememberMeAuthenticationFilter 로직 */
        //Token Cookie이 존재하면 추출
        //Decode Token하여 토큰이 정상인지 판단
        //사용자가 들고온 토큰과 서버에 저장된 토큰이 서로 일치하는지 판단
        //토큰에 저장된 정보를 이용해 DB에 해당 User 계정이 존재하는지 판단
        //위 조건을 모두 통과하면 새로운 인증객체(Authentication)을 생성 후 AuthenticationManager에게 인증 처리를 넘긴다. (물론 Security Context에도 인증 객체를 저장한다.)
        //이후 response 될 때 JSESSIONID를 다시 보내준다.

        //http.authenticationProvider(authenticationProvider); // 로그인 요청 시 들어오는 provider 등록

        return http.build();
    }

    // node_module & static resource 접근 x허용
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .mvcMatchers("/node_modules/**", "/images/**" ,"/resources/**" );
    }

    //  series 와 token 값을 랜덤으로 생성, 브루트 포스(brute force) 공격에 대비
    @Bean
    public PersistentTokenBasedRememberMeServices rememberMeServices(final PersistentTokenRepository tokenRepository) {
        PersistentTokenBasedRememberMeServices rememberMeServices = new PersistentTokenBasedRememberMeServices(REMEMBER_ME_TOKEN_KEY, principalDetailService , tokenRepository);
        rememberMeServices.setParameter("remember-me");
        rememberMeServices.setCookieName("remember-me");
        rememberMeServices.setAlwaysRemember(true);
        return rememberMeServices;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(final PersistentLoginRepository repository) {
        return new JpaPersistentTokenRepository(repository);
    }



}

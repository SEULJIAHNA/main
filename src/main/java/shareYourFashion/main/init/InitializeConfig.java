package shareYourFashion.main.init;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import shareYourFashion.main.constant.JoinPath;
import shareYourFashion.main.constant.Roles;
import shareYourFashion.main.constant.Sex;
import shareYourFashion.main.domain.BackgroundProfileImage;
import shareYourFashion.main.domain.User;
import shareYourFashion.main.domain.UserProfileImage;
import shareYourFashion.main.repository.BackgroundProfileImageRepository;
import shareYourFashion.main.repository.ProfileImageRepository;
import shareYourFashion.main.repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class InitializeConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final ProfileImageRepository profileImageRepository;

    @Autowired
    private final BackgroundProfileImageRepository backgroundProfileImageRepository;

    @PostConstruct
    @Transactional
    public void init() {

        UserProfileImage admin_profileImage = UserProfileImage.createDefaultProfileImageEntity();
        UserProfileImage user_profileImage = UserProfileImage.createDefaultProfileImageEntity();

        BackgroundProfileImage admin_bgProfileImage =BackgroundProfileImage.createDefaultBackgroundProfileImageEntity();
        BackgroundProfileImage user_bgProfileImage =BackgroundProfileImage.createDefaultBackgroundProfileImageEntity();



        User admin = new User().builder()
                .roles(Roles.ADMIN).age("26")
                .nickname("admin").email("agh0314@naver.com")
                .joinPath(JoinPath.DEFAULT_ACCOUNT)
                .backgroundImage(admin_bgProfileImage)
                .profileImage(admin_profileImage)
                .sex(Sex.MAN).password(passwordEncoder.encode("agh@p970314"))
                .build();

        User user = User.builder()
                .nickname("testUser")
                .email("agh0315@gmail.com")
                .password(passwordEncoder.encode("1234"))
                .roles(Roles.USER).age("26").joinPath(JoinPath.DEFAULT_ACCOUNT)
                .backgroundImage(user_bgProfileImage)
                .profileImage(user_profileImage)
                .build();

        if(!userRepository.existsByEmail(user.getEmail()))
            userRepository.saveAll(Arrays.asList(admin, user));

        backgroundProfileImageRepository.saveAll(Arrays.asList(admin_bgProfileImage , user_bgProfileImage));
        profileImageRepository.saveAll(Arrays.asList(user_profileImage , admin_profileImage));
    }
}

package shareYourFashion.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shareYourFashion.main.domain.BackgroundProfileImage;
import shareYourFashion.main.domain.PersistentLogin;
import shareYourFashion.main.domain.UserProfileImage;
import shareYourFashion.main.domain.valueTypeClass.Image;

import java.util.Optional;

@Repository
public interface BackgroundProfileImageRepository  extends JpaRepository<BackgroundProfileImage, Long> {

    Optional<BackgroundProfileImage> findByImageFileName(String fileName);

}

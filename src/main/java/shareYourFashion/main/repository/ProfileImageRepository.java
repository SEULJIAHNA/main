package shareYourFashion.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shareYourFashion.main.domain.UserProfileImage;
import shareYourFashion.main.domain.valueTypeClass.Image;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileImageRepository extends JpaRepository<UserProfileImage, Long> {

    Optional<UserProfileImage> findByImageFileName(String fileName);
}

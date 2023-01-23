package shareYourFashion.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shareYourFashion.main.domain.PersistentLogin;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersistentLoginRepository extends JpaRepository<PersistentLogin, String> {

    Optional<PersistentLogin> findBySeries(final String series);

    List<PersistentLogin> findByUsername(final String username);



}

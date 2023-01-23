package shareYourFashion.main.repository;


import shareYourFashion.main.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    Optional<User> findById(Long id);

    // 파라미터로 오는 userId 값이 db에 저장되어 있는지 확인
    boolean existsById(Long userId);

    // 이메일 중복 확인 (있으면 true , 없으면 false)
    boolean existsByEmail(String email);

    // 닉네임 중복 확인 (있으면 true , 없으면 false)
    boolean existsByNickname(String nickname);


}

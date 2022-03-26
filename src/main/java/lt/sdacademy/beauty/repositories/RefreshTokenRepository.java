package lt.sdacademy.beauty.repositories;

import lt.sdacademy.beauty.models.RefreshToken;
import lt.sdacademy.beauty.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Override
    Optional<RefreshToken> findById(Long id);
    Optional<RefreshToken> findByToken(String token);
    Long deleteByUser(User user);

}

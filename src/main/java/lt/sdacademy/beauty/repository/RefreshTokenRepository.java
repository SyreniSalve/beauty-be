package lt.sdacademy.beauty.repository;

import lt.sdacademy.beauty.model.RefreshTokenEntity;
import lt.sdacademy.beauty.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    @Override
    Optional<RefreshTokenEntity> findById(Long id);
    Optional<RefreshTokenEntity> findByToken(String token);
    Long deleteByUser(UserEntity user);

}

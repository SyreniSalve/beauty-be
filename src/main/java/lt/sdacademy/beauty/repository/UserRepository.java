package lt.sdacademy.beauty.repository;

import lt.sdacademy.beauty.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findById(Long id);
    void deleteById(Long id);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE CONCAT(u.firstName, ' ', u.lastName, ' ', u.username, ' ', u.email, ' ', u.jobTitle," +
            "' ', u.city, ' ', u.state) LIKE %?1%")
    Page<UserEntity> findAll(String keyword, Pageable pageable);
}

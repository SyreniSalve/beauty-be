package lt.sdacademy.beauty.repository;

import lt.sdacademy.beauty.model.entity.Role;
import lt.sdacademy.beauty.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findById(Long id);
    UserEntity findUserEntitiesById(Long id);
    void deleteById(Long id);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r.role=:role")
    Page<UserEntity> findAllByRoles(@Param("role")Role roleOwner, Pageable pageable);

//    @Query("SELECT u from UserEntity u where concat (u.firstName, " +
//            "' ', u.lastName, ' ', u.username, ' ', u.email, ' ', u.jobTitle," +
//            "' ', u.city, ' ', u.state) LIKE %?1% ")
//    Page<UserEntity> findAllByRoles(@Param("role")Role roleOwner, String keyword, Pageable pageable);

    @Query("SELECT u FROM UserEntity u WHERE CONCAT(u.firstName, ' ', u.lastName, ' ', u.username, ' ', u.email, ' ', u.jobTitle," +
            "' ', u.city, ' ', u.state) LIKE %?1%")
    Page<UserEntity> findAll(String keyword, Pageable pageable);

}

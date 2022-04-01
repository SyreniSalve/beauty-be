package lt.sdacademy.beauty.repository;

import lt.sdacademy.beauty.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
    void deleteById(Long id);
    Page<UserEntity> findAll(Pageable pageable);
//    Page<List<UserEntity>> findAllByFirstNameAndLastName(String firstName, String lastName, Pageable pageable);
//    Page<List<UserEntity>> findAllByJobTitle(String jobTitle, Pageable pageable);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}

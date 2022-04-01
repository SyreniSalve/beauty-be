package lt.sdacademy.beauty.repository;

import lt.sdacademy.beauty.model.RoleEntity;
import lt.sdacademy.beauty.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByRole(Role role);

}

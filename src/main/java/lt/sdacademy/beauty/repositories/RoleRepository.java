package lt.sdacademy.beauty.repositories;

import lt.sdacademy.beauty.models.RoleEntity;
import lt.sdacademy.beauty.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByRole(Role role);

}

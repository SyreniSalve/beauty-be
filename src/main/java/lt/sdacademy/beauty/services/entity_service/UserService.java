package lt.sdacademy.beauty.services.entity_service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.sdacademy.beauty.models.Role;
import lt.sdacademy.beauty.models.RoleEntity;
import lt.sdacademy.beauty.models.UserEntity;
import lt.sdacademy.beauty.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public List<UserEntity> findAllOwners(){
        try {
            List<UserEntity> usersList = this.userRepository.findAll();
            List<UserEntity> ownersList = new ArrayList<>();
            for (UserEntity user: usersList) {
                for (RoleEntity role: user.getRoles()) {
                    if (role.getRole().equals(Role.ROLE_OWNER)) {
                        ownersList.add(user);
                    }
                }
            }
            return ownersList;
        } catch (Exception e) {
            throw new RuntimeException("There are no owner in database.");
        }

    }
}

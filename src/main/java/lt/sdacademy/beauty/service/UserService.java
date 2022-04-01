package lt.sdacademy.beauty.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.sdacademy.beauty.model.Role;
import lt.sdacademy.beauty.model.RoleEntity;
import lt.sdacademy.beauty.model.UserEntity;
import lt.sdacademy.beauty.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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

    public UserEntity updateUser(UserEntity user) {
        try {
            log.info("Updating user: {}", user.getUsername());
            if (user.getImageUrl() == null) {
                user.setImageUrl(setUserImageUrl());
            }
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("User not updated.");
        }
    }

    public void deleteUser(Long id) {
        log.info("Delete user with id: {}", id);
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Username %s not found.", id));
        }
    }


    public String setUserImageUrl() {
        String[] imagesNames = {"user.png", "user(2).png", "user(3).png", "user(4).png"};
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/auth/image/" + imagesNames[new Random()
                .nextInt(4)]).toUriString();
    }
}

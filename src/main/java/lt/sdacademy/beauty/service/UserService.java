package lt.sdacademy.beauty.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.sdacademy.beauty.model.entity.Role;
import lt.sdacademy.beauty.model.entity.RoleEntity;
import lt.sdacademy.beauty.model.entity.UserEntity;
import lt.sdacademy.beauty.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.*;


@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public Optional<UserEntity> findById(Long id) {
       return Optional.ofNullable(this.userRepository.findById(id)
               .orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }

    public Optional<UserEntity> findByUsername(String username){
        return Optional.ofNullable(this.userRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

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

    public Map<String, Object> findAllByKeyword(String keyword, int page, int size) {
        try {
            List<UserEntity> users;
            Page<UserEntity> pages;
            if (keyword == null) {
                pages = userRepository.findAll(PageRequest.of(page, size));
            } else {
                pages = userRepository.findAll(keyword, PageRequest.of(page, size));
            }
            users = pages.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("users", users);
            response.put("currentPage", pages.getNumber());
            response.put("totalItems", pages.getTotalElements());
            response.put("totalPages", pages.getTotalPages());
            return response;
        } catch (Exception e) {
            throw new NullPointerException("Internal server error");
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

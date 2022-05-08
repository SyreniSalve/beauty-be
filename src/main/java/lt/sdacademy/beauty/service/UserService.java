package lt.sdacademy.beauty.service;

import lombok.extern.slf4j.Slf4j;
import lt.sdacademy.beauty.model.entity.EventEntity;
import lt.sdacademy.beauty.model.entity.Role;
import lt.sdacademy.beauty.model.entity.RoleEntity;
import lt.sdacademy.beauty.model.entity.UserEntity;
import lt.sdacademy.beauty.repository.EventEntityRepository;
import lt.sdacademy.beauty.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;


@Service
@Transactional
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventEntityRepository eventEntityRepository;


    public Optional<UserEntity> findById(Long id) {
       return Optional.ofNullable(this.userRepository.findById(id)
               .orElseThrow(() -> new IllegalArgumentException("User not found")));
    }

    public Optional<UserEntity> findByUsername(String username){
        return Optional.ofNullable(this.userRepository.findByUsername(username))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public Map<String, Object> findAllByRoleOwner(String keyword, int page, int size){
        List<UserEntity> owners;
        Page<UserEntity> pages;
        if (keyword == null) {
            pages = userRepository.findAllByRoles(Role.ROLE_OWNER, PageRequest.of(page, size));
        } else {
            pages = userRepository.findAllByRoles(Role.ROLE_OWNER, PageRequest.of(page, size));
        }
        owners = pages.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("owners", owners);
        response.put("currentPage", pages.getNumber());
        response.put("totalItems", pages.getTotalElements());
        response.put("totalPages", pages.getTotalPages());
        return response;
    }


    public Map<String, Object> findAllByKeyword(String keyword, int page, int size) {
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
    }

    public UserEntity adminUpdate(Long id, UserEntity userRequest){
        UserEntity user = this.userRepository.findUserEntitiesById(id);
        user.setUsername(user.getUsername());
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setJobTitle(userRequest.getJobTitle());
        user.setPhone(user.getPhone());
        user.setEmail(user.getEmail());
        user.setCity(user.getCity());
        user.setState(user.getState());
        user.setImageUrl(user.getImageUrl());
        Set<RoleEntity> roles = user.getRoles();
        user.setRoles(userRequest.getRoles());
        roles.forEach(role -> {
            user.getRoles().add(role);
        });
        return this.userRepository.save(user);
    }


    public UserEntity updateUser(Long id, UserEntity userRequest) {
        UserEntity user = this.userRepository.findUserEntitiesById(id);
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setJobTitle(userRequest.getJobTitle());
        user.setPhone(userRequest.getPhone());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setEmail(userRequest.getEmail());
        user.setCity(userRequest.getCity());
        user.setState(userRequest.getState());
        user.setImageUrl(userRequest.getImageUrl());
        user.setRoles(user.getRoles());
        return this.userRepository.save(user);
    }

    public void deleteUserRole(Long roleId, Long userId) {
        UserEntity foundUser = this.userRepository.findUserEntitiesById(userId);
        Set<RoleEntity> roles = foundUser.getRoles();
        roles.removeIf(role -> role.getId().equals(roleId));
    }

    public UserEntity addEvent(Long userId, EventEntity event) {
        UserEntity user = this.userRepository.findUserEntitiesById(userId);
        EventEntity newEvent = this.eventEntityRepository.save(event);
        user.getEvents().add(newEvent);
        return user;
    }

    public void deleteUser(Long id) {
        log.info("Delete user with id: {}", id);
        userRepository.deleteById(id);
    }

}



package lt.sdacademy.beauty.service;

import lombok.extern.slf4j.Slf4j;
import lt.sdacademy.beauty.model.entity.RoleEntity;
import lt.sdacademy.beauty.model.entity.UserEntity;
import lt.sdacademy.beauty.repository.RoleRepository;
import lt.sdacademy.beauty.repository.UserRepository;
import lt.sdacademy.beauty.model.dto.request.SignupRequest;
import lt.sdacademy.beauty.model.dto.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.Set;
import static lt.sdacademy.beauty.model.entity.Role.ROLE_OWNER;
import static lt.sdacademy.beauty.model.entity.Role.ROLE_USER;

@Service
@Transactional
@Slf4j
public class RegistrationService {

    private final String errorText = "Error: Role is not found.";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    public ResponseEntity<MessageResponse> validationControl(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        } else if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        return null;
    }

    public UserEntity userRegistration(SignupRequest signupRequest) {
        validationControl(signupRequest);
        UserEntity user = new UserEntity(signupRequest.getUsername(),
                signupRequest.getFirstName(),
                signupRequest.getLastName(),
                signupRequest.getJobTitle(),
                signupRequest.getPhone(),
                signupRequest.getDateOfBirth(),
                signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()),
                signupRequest.getCity(),
                signupRequest.getState(),
                signupRequest.getImageUrl());
        Set<String> strRoles = signupRequest.getRole();
        Set<RoleEntity> roles = new HashSet<>();
        if (strRoles == null || strRoles.size() == 0) {
            RoleEntity userRole = roleRepository.findByRole(ROLE_USER)
                    .orElseThrow(() -> new RuntimeException(errorText));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if ("owner".equals(role)) {
                    RoleEntity ownerRole = roleRepository.findByRole(ROLE_OWNER)
                            .orElseThrow(() -> new RuntimeException(errorText));
                    roles.add(ownerRole);
                } else {
                    RoleEntity userRole = roleRepository.findByRole(ROLE_USER)
                            .orElseThrow(() -> new RuntimeException(errorText));
                    roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        return userRepository.save(user);
    }
}

package lt.sdacademy.beauty.controllers;


import lombok.extern.slf4j.Slf4j;
import lt.sdacademy.beauty.models.RefreshTokenEntity;
import lt.sdacademy.beauty.models.RoleEntity;
import lt.sdacademy.beauty.models.UserEntity;
import lt.sdacademy.beauty.repositories.RoleRepository;
import lt.sdacademy.beauty.repositories.UserRepository;
import lt.sdacademy.beauty.security.jwt.JwtUtils;
import lt.sdacademy.beauty.security.jwt.exception.TokenRefreshException;
import lt.sdacademy.beauty.security.jwt.payload.request.LoginRequest;
import lt.sdacademy.beauty.security.jwt.payload.request.SignupRequest;
import lt.sdacademy.beauty.security.jwt.payload.request.TokenRefreshRequest;
import lt.sdacademy.beauty.security.jwt.payload.response.JwtResponse;
import lt.sdacademy.beauty.security.jwt.payload.response.MessageResponse;
import lt.sdacademy.beauty.security.jwt.payload.response.TokenRefreshResponse;
import lt.sdacademy.beauty.security.jwt.services.RefreshTokenService;
import lt.sdacademy.beauty.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static lt.sdacademy.beauty.models.Role.ROLE_OWNER;
import static lt.sdacademy.beauty.models.Role.ROLE_USER;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final String errorText = "Error: Role is not found.";

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(), userDetails.getUsername(),
                userDetails.getEmail(), roles));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshTokenEntity::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        UserEntity user = new UserEntity(signupRequest.getUsername(),
                             signupRequest.getEmail(),
                             encoder.encode(signupRequest.getPassword()));
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
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}

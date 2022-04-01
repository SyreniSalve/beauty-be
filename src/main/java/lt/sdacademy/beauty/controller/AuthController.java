package lt.sdacademy.beauty.controller;

import lombok.extern.slf4j.Slf4j;
import lt.sdacademy.beauty.model.UserEntity;
import lt.sdacademy.beauty.security.jwt.payload.request.LoginRequest;
import lt.sdacademy.beauty.security.jwt.payload.request.SignupRequest;
import lt.sdacademy.beauty.security.jwt.payload.request.TokenRefreshRequest;
import lt.sdacademy.beauty.security.jwt.payload.response.MessageResponse;
import lt.sdacademy.beauty.service.LoginService;
import lt.sdacademy.beauty.service.RegistrationService;
import lt.sdacademy.beauty.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.util.MimeTypeUtils.IMAGE_PNG_VALUE;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    RegistrationService registrationService;

    @Autowired
    LoginService loginService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(this.loginService.createLoginRequest(loginRequest), HttpStatus.CREATED);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        return new ResponseEntity<>(this.loginService.refreshToken(request), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> userRegistration(@Valid @RequestBody SignupRequest signupRequest) {
        this.registrationService.userRegistration(signupRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @DeleteMapping("/delete_user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update_user")
    public ResponseEntity<UserEntity> updateUser(@RequestBody UserEntity user) {
        UserEntity updatedUser = this.userService.updateUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping(path = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get("src/main/java/lt/sdacademy/beauty/server/image/" + fileName));

    }
}

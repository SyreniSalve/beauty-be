package lt.sdacademy.beauty.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.extern.slf4j.Slf4j;
import lt.sdacademy.beauty.model.dto.*;
import lt.sdacademy.beauty.model.entity.EventEntity;
import lt.sdacademy.beauty.model.entity.UserEntity;
import lt.sdacademy.beauty.model.dto.request.LoginRequest;
import lt.sdacademy.beauty.model.dto.request.SignupRequest;
import lt.sdacademy.beauty.model.dto.request.TokenRefreshRequest;
import lt.sdacademy.beauty.model.dto.response.MessageResponse;
import lt.sdacademy.beauty.service.EventService;
import lt.sdacademy.beauty.service.LoginService;
import lt.sdacademy.beauty.service.RegistrationService;
import lt.sdacademy.beauty.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

import static org.springframework.util.MimeTypeUtils.IMAGE_PNG_VALUE;

@Slf4j
@CrossOrigin()
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private EventService eventService;

    @Autowired
    private ModelMapper modelMapper;


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

    @GetMapping("/user/{id}")
    public ResponseEntity<Optional<UserEntity>> findUserById(@PathVariable("id") Long id) {
        Optional<UserEntity> user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/find_by_username/{username}")
    public ResponseEntity<Optional<UserEntity>> findUserByUsername(@PathVariable("username") String username) {
        Optional<UserEntity> user = userService.findByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(path = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get("src/main/java/lt/sdacademy/beauty/server/image/" + fileName));

    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @JsonInclude()
    public ResponseEntity<Map<String, Object>> findAllUsersByKeyword(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {
        return new ResponseEntity<>(userService.findAllByKeyword(keyword, page, size), HttpStatus.OK);
    }

    @PostMapping("/add-event/user/{userId}")
    public ResponseEntity<EventEntityDto> createEvent(@PathVariable("userId")  Long userId,
                                                   @RequestBody EventEntityDto eventParams) {
        EventEntity userRequest = modelMapper.map(eventParams, EventEntity.class);
        EventEntity userEvent = eventService.createEvent(userRequest);
        this.userService.addEvent(userId, userEvent);
        EventEntityDto postResponse = modelMapper.map(userEvent, EventEntityDto.class);
        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }


    @PutMapping("/update_user/{id}")
    public ResponseEntity<UserDto> userUpdateForUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        UserEntity userRequest = modelMapper.map(userDto, UserEntity.class);
        UserEntity user = userService.updateUser(id, userRequest);
        UserDto postResponse = modelMapper.map(user, UserDto.class);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }


    @PutMapping("/admin_update/{id}")
    public ResponseEntity<AdminDto> userUpdateForAdmin(@PathVariable("id") Long id, @RequestBody AdminDto adminDto) {
        UserEntity userRequest = modelMapper.map(adminDto, UserEntity.class);
        UserEntity user = userService.adminUpdate(id, userRequest);
        AdminDto postResponse = modelMapper.map(user, AdminDto.class);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete_user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete_role/{role_id}/{user_id}")
    public ResponseEntity<UserEntity> deleteUserRole(@PathVariable("role_id") Long roleId,
                                                     @PathVariable("user_id") Long userId) {
        this.userService.deleteUserRole(roleId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

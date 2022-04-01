package lt.sdacademy.beauty.controller;

import lombok.RequiredArgsConstructor;
import lt.sdacademy.beauty.model.entity.UserEntity;
import lt.sdacademy.beauty.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/test")
public class TestController {

    private final UserService userService;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/all/owners")
    public ResponseEntity<List<UserEntity>> findAllOwners() {
        List<UserEntity> ownersList = this.userService.findAllOwners();
        return new ResponseEntity<>(ownersList, HttpStatus.OK);
    }
}

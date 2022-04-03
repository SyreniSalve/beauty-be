package lt.sdacademy.beauty.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.RequiredArgsConstructor;
import lt.sdacademy.beauty.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private UserService userService;

//    @GetMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String adminAccess() {
//        return "Admin Board";
//    }

//    @GetMapping("/users")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @JsonInclude()
//    public ResponseEntity<Map<String, Object>> findAllUsersByKeyword(
//            @RequestParam(required = false) String keyword,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "3") int size) {
//        return new ResponseEntity<>(userService.findAllByKeyword(keyword, page, size), HttpStatus.OK);
//    }
//    @GetMapping("/users")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @JsonInclude()
//    public ResponseEntity<Map<String, Object>> findAllUsersByKeyword(
//            @RequestParam(required = false) String keyword,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "3") int size) {
//        return new ResponseEntity<>(userService.findAllByKeyword(keyword, page, size), HttpStatus.OK);
//    }

//    @DeleteMapping("/admin/delete_user/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
//        userService.deleteUser(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}

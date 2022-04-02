package lt.sdacademy.beauty.controller;

import lombok.RequiredArgsConstructor;
import lt.sdacademy.beauty.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/test")
public class AdminController {

    private UserService userService;

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminAccess() {
        return "Admin Board";
    }

//    @DeleteMapping("/admin/delete_user/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
//        userService.deleteUser(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}

package lt.sdacademy.beauty.controller;

import lombok.RequiredArgsConstructor;
import lt.sdacademy.beauty.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> findOwners(@RequestParam(required = false) String keyword,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "4") int size){
        return new ResponseEntity<>(this.userService.findAllByRoleOwner(keyword, page, size), HttpStatus.OK);
    }
}

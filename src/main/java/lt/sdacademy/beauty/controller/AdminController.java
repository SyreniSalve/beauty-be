package lt.sdacademy.beauty.controller;

import lombok.RequiredArgsConstructor;
import lt.sdacademy.beauty.service.UserService;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private UserService userService;

}

package lt.sdacademy.beauty.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/test")
public class OwnerController {

    @GetMapping("/owner")
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public String ownerAccess() {
        return "Owner Schedule";
    }

}

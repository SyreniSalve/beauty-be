package lt.sdacademy.beauty.security.jwt.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class SignupRequest {

    @NotBlank
    @Size(min = 3, max = 250)
    private String username;

    @NotBlank
    @Size(max = 250)
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}

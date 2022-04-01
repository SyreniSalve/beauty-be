package lt.sdacademy.beauty.security.jwt.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JwtResponse {

    private String accessToken;
    private String tokenType = "Bearer";
    private String refreshToken;
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String phone;
    private LocalDate dateOfBirth;
    private String email;
    private String city;
    private String state;
    private String imageUrl;
    private List<String> roles;

    public JwtResponse(String accessToken, String refreshToken, Long id, String username,
                       String firstName, String lastName, String jobTitle, String phone,
                       LocalDate dateOfBirth, String email, String city, String state,
                       String imageUrl, List<String> roles) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.city = city;
        this.state = state;
        this.imageUrl = imageUrl;
        this.roles = roles;
    }
}

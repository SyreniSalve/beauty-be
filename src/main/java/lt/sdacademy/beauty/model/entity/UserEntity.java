package lt.sdacademy.beauty.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table (name = "user",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class UserEntity extends AbstractEntity {

    @NotBlank
    @Size(max = 250)
    private String username;

    @Column(name = "first_name")
    @Size(max = 250)
    @NotBlank
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    @Size(max = 250)
    private String lastName;

    @NotBlank
    @Size(max = 120)
    @Column(name = "job_title")
    private String jobTitle;

    @NotBlank
    @Size(max = 12)
    private String phone;

    @Column(name = "date_of_birth")
    @JsonSerialize(using = LocalDateSerializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate dateOfBirth;

    @NotBlank
    @Size(max = 250)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @Column(name = "image_url")
    private String imageUrl;

    public UserEntity(String username, String firstName, String lastName,
                      String jobTitle, String phone, LocalDate dateOfBirth,
                      String email, String password, String city, String state, String imageUrl) {
       this.username = username;
       this.firstName = firstName;
       this.lastName = lastName;
       if (jobTitle == null) {
           this.jobTitle = "user";
       }
       this.phone = phone;
       this.email = email;
       this.dateOfBirth = dateOfBirth;
       this.password = password;
       this.city = city;
       this.state = state;
       this.imageUrl = imageUrl;
    }
}

package lt.sdacademy.beauty.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity(name = "refresh_token")
public class RefreshTokenEntity extends AbstractEntity {

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;
}

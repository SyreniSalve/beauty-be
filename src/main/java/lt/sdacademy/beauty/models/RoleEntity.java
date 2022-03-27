package lt.sdacademy.beauty.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
public class RoleEntity extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Role role;
}

package lt.sdacademy.beauty.model.dto;

import lombok.Data;
import lt.sdacademy.beauty.model.entity.RoleEntity;
import javax.validation.constraints.Size;
import java.util.Set;


@Data
public class AdminDto {

    @Size(max = 120)
    private String jobTitle;

    private Set<RoleEntity> roles;

}


package mx.edu.utez.rbbackcomite.models.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto {
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 30, message = "El nombre no debe superar los 30 caracteres")
    private String name;

    public RoleEntity toEntity() {
        RoleEntity entity = new RoleEntity();
        entity.setId(this.id);
        entity.setName(this.name);
        return entity;
    }
}

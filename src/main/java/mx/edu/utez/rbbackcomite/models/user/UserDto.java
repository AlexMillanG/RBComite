package mx.edu.utez.rbbackcomite.models.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(min = 4, max = 20, message = "El nombre de usuario debe tener entre 4 y 20 caracteres")
    private String username;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotBlank(message = "El nombre completo no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre completo debe tener entre 3 y 100 caracteres")
    private String fullName;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe tener 10 dígitos")
    private String phone;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe tener un formato válido")
    private String email;

    @NotNull(message = "El ID del rol no puede ser nulo")
    @Positive(message = "El ID del rol debe ser un número positivo")
    private Long roleId;

    @Positive(message = "El ID del grupo debe ser un número positivo")
    private Long groupId;

    public UserEntity toEntity() {
        UserEntity user = new UserEntity();
        user.setUsername(this.username);
        user.setPassword(this.password); // Nota: En producción debería estar encriptada
        user.setFullName(this.fullName);
        user.setPhone(this.phone);
        user.setEmail(this.email);
        return user;
    }
}
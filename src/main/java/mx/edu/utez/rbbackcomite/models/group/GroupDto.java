package mx.edu.utez.rbbackcomite.models.group;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GroupDto {
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El municipio es obligatorio")
    private String municipality;

    @NotBlank(message = "La colonia es obligatoria")
    private String neighborhood;

    public GroupEntity toEntity() {
        GroupEntity group = new GroupEntity();
        group.setName(this.name);
        group.setMunicipality(this.municipality);
        group.setNeighborhood(this.neighborhood);
        return group;
    }
}

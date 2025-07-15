package mx.edu.utez.rbbackcomite.models.eventType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventTypeDto {
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no debe superar los 50 caracteres")
    private String name;

    public EventTypeEntity toEntity() {
        EventTypeEntity entity = new EventTypeEntity();
        entity.setId(this.id);
        entity.setName(this.name);
        return entity;
    }
}

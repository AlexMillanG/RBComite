package mx.edu.utez.rbbackcomite.models.event;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventDto {
    @NotBlank(message = "El título no puede estar vacío")
    @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres")
    private String title;

    @NotNull(message = "La fecha no puede ser nula")
    @FutureOrPresent(message = "La fecha debe ser hoy o en el futuro")
    private LocalDate date;

    @NotBlank(message = "El estado no puede estar vacío")
    @Pattern(regexp = "PRÓXIMAMENTE|EN_EJECUCIÓN|FINALIZADO",
            message = "El estado debe ser PRÓXIMAMENTE, EN_EJECUCIÓN o FINALIZADO")
    private String status;

    @NotNull(message = "El ID del tipo de evento no puede ser nulo")
    @Positive(message = "El ID del tipo de evento debe ser un número positivo")
    private Long typeId;

    @NotNull(message = "El ID del grupo no puede ser nulo")
    @Positive(message = "El ID del grupo debe ser un número positivo")
    private Long groupId;

    private List<@Positive(message = "Cada ID de participante debe ser un número positivo") Long> participantIds;

    public EventEntity toEntity() {
        EventEntity event = new EventEntity();
        event.setTitle(this.title);
        event.setDate(this.date);
        event.setStatus(EventStatus.valueOf(this.status));
        return event;
    }
}
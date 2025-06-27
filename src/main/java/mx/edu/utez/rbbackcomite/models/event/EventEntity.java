package mx.edu.utez.rbbackcomite.models.event;

import jakarta.persistence.*;
import lombok.Data;
import mx.edu.utez.rbbackcomite.models.eventType.EventTypeEntity;
import mx.edu.utez.rbbackcomite.models.group.GroupEntity;
import mx.edu.utez.rbbackcomite.models.user.UserEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
@Data
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private EventStatus status; // "PRÓXIMAMENTE", "EN_EJECUCIÓN", "FINALIZADO"

    @ManyToOne
    @JoinColumn(name = "type_id")
    private EventTypeEntity type;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    @ManyToMany
    @JoinTable(
            name = "event_participants",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> participants = new ArrayList<>();


}
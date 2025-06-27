package mx.edu.utez.rbbackcomite.models.eventType;

import jakarta.persistence.*;
import lombok.Data;
import mx.edu.utez.rbbackcomite.models.event.EventEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "event_types")
@Data
public class EventTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // "Limpieza", "Reforestación", etc.

    @OneToMany(mappedBy = "type")
    private List<EventEntity> events = new ArrayList<>();


}
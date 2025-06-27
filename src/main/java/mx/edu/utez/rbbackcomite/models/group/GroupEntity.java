package mx.edu.utez.rbbackcomite.models.group;

import jakarta.persistence.*;
import lombok.Data;
import mx.edu.utez.rbbackcomite.models.event.EventEntity;
import mx.edu.utez.rbbackcomite.models.user.UserEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
@Data
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String municipality;
    private String neighborhood;

    @OneToMany(mappedBy = "group")
    private List<UserEntity> members;

    @OneToMany(mappedBy = "group")
    private List<EventEntity> events = new ArrayList<>();


}
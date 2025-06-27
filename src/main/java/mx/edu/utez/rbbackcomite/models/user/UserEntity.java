package mx.edu.utez.rbbackcomite.models.user;

import jakarta.persistence.*;
import lombok.Data;
import mx.edu.utez.rbbackcomite.models.event.EventEntity;
import mx.edu.utez.rbbackcomite.models.group.GroupEntity;
import mx.edu.utez.rbbackcomite.models.role.RoleEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String fullName;
    private String phone;
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    @ManyToMany(mappedBy = "participants")
    private List<EventEntity> events = new ArrayList<>();

}
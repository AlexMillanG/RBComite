package mx.edu.utez.rbbackcomite.models.role;

import jakarta.persistence.*;
import lombok.Data;
import mx.edu.utez.rbbackcomite.models.user.UserEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // "ADMIN", "GROUP_ADMIN", "MEMBER"

    @OneToMany(mappedBy = "role")
    private List<UserEntity> users = new ArrayList<>();


}
package mx.edu.utez.rbbackcomite.models.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity,Long> {
    boolean existsByName(String name);
}

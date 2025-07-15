package mx.edu.utez.rbbackcomite.models.eventType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTypeRepository extends JpaRepository<EventTypeEntity,Long> {
     boolean existsByName(String b);
}

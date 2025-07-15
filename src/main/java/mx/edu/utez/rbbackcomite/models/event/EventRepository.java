package mx.edu.utez.rbbackcomite.models.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity,Long> {

    List<EventEntity> findByStatus(EventStatus status);
    List<EventEntity> findByGroupId(Long groupId);
    List<EventEntity> findByTypeId(Long typeId);

}

package mx.edu.utez.rbbackcomite.services.event;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.rbbackcomite.config.ApiResponse;
import mx.edu.utez.rbbackcomite.models.event.EventDto;
import mx.edu.utez.rbbackcomite.models.event.EventEntity;
import mx.edu.utez.rbbackcomite.models.event.EventRepository;
import mx.edu.utez.rbbackcomite.models.event.EventStatus;
import mx.edu.utez.rbbackcomite.models.eventType.EventTypeRepository;
import mx.edu.utez.rbbackcomite.models.group.GroupRepository;
import mx.edu.utez.rbbackcomite.models.user.UserEntity;
import mx.edu.utez.rbbackcomite.models.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EventServices {

    private final EventRepository repository;
    private final EventTypeRepository eventTypeRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public ResponseEntity<ApiResponse> getAll() {
        List<EventEntity> events = repository.findAll();
        return ResponseEntity.ok(new ApiResponse(events, false, "Eventos encontrados"));
    }

    public ResponseEntity<ApiResponse> getOne(Long id) {
        Optional<EventEntity> found = repository.findById(id);
        if (found.isPresent()) {
            return ResponseEntity.ok(new ApiResponse(found.get(), false, "Evento encontrado"));
        }
        return ResponseEntity.status(404).body(new ApiResponse(null, true, "Evento no encontrado"));
    }

    public ResponseEntity<ApiResponse> insert(EventDto dto) {
        // Verificar si el tipo de evento existe
        var eventType = eventTypeRepository.findById(dto.getTypeId());
        if (eventType.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse(null, true, "Tipo de evento no encontrado"));
        }

        // Verificar si el grupo existe
        var group = groupRepository.findById(dto.getGroupId());
        if (group.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse(null, true, "Grupo no encontrado"));
        }

        // Crear la entidad del evento
        EventEntity event = new EventEntity();
        event.setTitle(dto.getTitle());
        event.setDate(dto.getDate());
        event.setStatus(EventStatus.valueOf(dto.getStatus()));
        event.setType(eventType.get());
        event.setGroup(group.get());

        // Guardar el evento
        EventEntity saved = repository.save(event);


        return ResponseEntity.status(201).body(new ApiResponse(saved, false, "Evento registrado correctamente"));
    }

    public ResponseEntity<ApiResponse> update(Long id, EventDto dto) {
        Optional<EventEntity> found = repository.findById(id);
        if (found.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse(null, true, "Evento no encontrado"));
        }

        // Verificar si el tipo de evento existe
        var eventType = eventTypeRepository.findById(dto.getTypeId());
        if (eventType.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse(null, true, "Tipo de evento no encontrado"));
        }

        // Verificar si el grupo existe
        var group = groupRepository.findById(dto.getGroupId());
        if (group.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse(null, true, "Grupo no encontrado"));
        }

        EventEntity entity = found.get();
        entity.setTitle(dto.getTitle());
        entity.setDate(dto.getDate());
        entity.setStatus(EventStatus.valueOf(dto.getStatus()));
        entity.setType(eventType.get());
        entity.setGroup(group.get());



        EventEntity updated = repository.save(entity);
        return ResponseEntity.ok(new ApiResponse(updated, false, "Evento actualizado correctamente"));
    }

    public ResponseEntity<ApiResponse> delete(Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(404).body(new ApiResponse(null, true, "Evento no encontrado"));
        }

        repository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse(null, false, "Evento eliminado correctamente"));
    }

    public ResponseEntity<ApiResponse> getEventsByStatus(String status) {
        try {
            EventStatus eventStatus = EventStatus.valueOf(status);
            List<EventEntity> events = repository.findByStatus(eventStatus);
            return ResponseEntity.ok(new ApiResponse(events, false, "Eventos encontrados por estado"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(null, true, "Estado de evento no válido"));
        }
    }

    public ResponseEntity<ApiResponse> getEventsByGroup(Long groupId) {
        if (!groupRepository.existsById(groupId)) {
            return ResponseEntity.badRequest().body(new ApiResponse(null, true, "Grupo no encontrado"));
        }
        List<EventEntity> events = repository.findByGroupId(groupId);
        return ResponseEntity.ok(new ApiResponse(events, false, "Eventos del grupo encontrados"));
    }

    public ResponseEntity<ApiResponse> getEventsByType(Long typeId) {
        if (!eventTypeRepository.existsById(typeId)) {
            return ResponseEntity.badRequest().body(new ApiResponse(null, true, "Tipo de evento no encontrado"));
        }
        List<EventEntity> events = repository.findByTypeId(typeId);
        return ResponseEntity.ok(new ApiResponse(events, false, "Eventos del tipo encontrados"));
    }

    public ResponseEntity<ApiResponse> setUsersToEvent(Long eventId, List<Long> userIds) {
        Optional<EventEntity> optionalEvent = repository.findById(eventId);
        if (optionalEvent.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse(null, true, "Evento no encontrado"));
        }

        EventEntity event = optionalEvent.get();

        List<UserEntity> users = new ArrayList<>();

        for (Long userId : userIds) {
            Optional<UserEntity> optionalUser = userRepository.findById(userId);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.badRequest().body(new ApiResponse(null, true, "Usuario con ID " + userId + " no encontrado"));
            }
            users.add(optionalUser.get());

        }

        event.setParticipants(users);
        EventEntity updatedEvent = repository.save(event);

        return ResponseEntity.ok(new ApiResponse(updatedEvent, false, "Usuarios asignados al evento correctamente"));
    }
}
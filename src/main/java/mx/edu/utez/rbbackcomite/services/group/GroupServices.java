package mx.edu.utez.rbbackcomite.services.group;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.rbbackcomite.config.ApiResponse;
import mx.edu.utez.rbbackcomite.models.group.GroupDto;
import mx.edu.utez.rbbackcomite.models.group.GroupEntity;
import mx.edu.utez.rbbackcomite.models.group.GroupRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupServices {

    private final GroupRepository groupRepository;

    public ResponseEntity<ApiResponse> save(@Valid GroupDto dto) {
        // Validar que no exista un grupo con el mismo nombre (opcional)
        if (groupRepository.existsByName(dto.getName())) {
            return new ResponseEntity<>(
                    new ApiResponse(null, true, "Ya existe un grupo con ese nombre"),
                    HttpStatus.BAD_REQUEST);
        }

        GroupEntity group = dto.toEntity();

        return new ResponseEntity<>(
                new ApiResponse(groupRepository.save(group), false, "Grupo registrado correctamente"),
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<ApiResponse> getAll() {
        return new ResponseEntity<>(new ApiResponse(groupRepository.findAll(), false, "OK"), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> getOne(Long id) {
        Optional<GroupEntity> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isEmpty())
            return new ResponseEntity<>(new ApiResponse(null, true, "Grupo no encontrado"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new ApiResponse(optionalGroup.get(), false, "OK"), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> update(Long id, @Valid GroupDto dto) {
        Optional<GroupEntity> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isEmpty())
            return new ResponseEntity<>(new ApiResponse(null, true, "Grupo no encontrado"), HttpStatus.NOT_FOUND);

        GroupEntity group = optionalGroup.get();

        // Validar que el nuevo nombre no choque con otro grupo distinto
        if (!group.getName().equals(dto.getName()) && groupRepository.existsByName(dto.getName())) {
            return new ResponseEntity<>(
                    new ApiResponse(null, true, "Ya existe otro grupo con ese nombre"),
                    HttpStatus.BAD_REQUEST);
        }

        group.setName(dto.getName());
        group.setMunicipality(dto.getMunicipality());
        group.setNeighborhood(dto.getNeighborhood());

        return new ResponseEntity<>(
                new ApiResponse(groupRepository.save(group), false, "Grupo actualizado correctamente"),
                HttpStatus.OK
        );
    }

    public ResponseEntity<ApiResponse> delete(Long id) {
        Optional<GroupEntity> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isEmpty())
            return new ResponseEntity<>(new ApiResponse(null, true, "Grupo no encontrado"), HttpStatus.NOT_FOUND);

        groupRepository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(null, false, "Grupo eliminado correctamente"), HttpStatus.OK);
    }
}

package mx.edu.utez.rbbackcomite.services.role;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.rbbackcomite.config.ApiResponse;
import mx.edu.utez.rbbackcomite.models.role.RoleDto;
import mx.edu.utez.rbbackcomite.models.role.RoleEntity;
import mx.edu.utez.rbbackcomite.models.role.RoleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServices {

    private final RoleRepository repository;

    public ResponseEntity<ApiResponse> getAll() {
        List<RoleEntity> roles = repository.findAll();
        return ResponseEntity.ok(new ApiResponse(roles, false, "Roles encontrados"));
    }

    public ResponseEntity<ApiResponse> getOne(Long id) {
        Optional<RoleEntity> found = repository.findById(id);
        if (found.isPresent()) {
            return ResponseEntity.ok(new ApiResponse(found.get(), false, "Rol encontrado"));
        }
        return ResponseEntity.status(404).body(new ApiResponse(null, true, "Rol no encontrado"));
    }

    public ResponseEntity<ApiResponse> insert(RoleDto dto) {
        if (repository.existsByName(dto.getName())) {
            return ResponseEntity.badRequest().body(new ApiResponse(null, true, "Ya existe un rol con ese nombre"));
        }

        RoleEntity saved = repository.save(dto.toEntity());
        return ResponseEntity.status(201).body(new ApiResponse(saved, false, "Rol registrado correctamente"));
    }

    public ResponseEntity<ApiResponse> update(Long id, RoleDto dto) {
        Optional<RoleEntity> found = repository.findById(id);
        if (found.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse(null, true, "Rol no encontrado"));
        }

        RoleEntity entity = found.get();
        entity.setName(dto.getName());

        RoleEntity updated = repository.save(entity);
        return ResponseEntity.ok(new ApiResponse(updated, false, "Rol actualizado correctamente"));
    }

    public ResponseEntity<ApiResponse> delete(Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(404).body(new ApiResponse(null, true, "Rol no encontrado"));
        }

        repository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse(null, false, "Rol eliminado correctamente"));
    }
}

package mx.edu.utez.rbbackcomite.services.user;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.rbbackcomite.config.ApiResponse;
import mx.edu.utez.rbbackcomite.models.group.GroupEntity;
import mx.edu.utez.rbbackcomite.models.group.GroupRepository;
import mx.edu.utez.rbbackcomite.models.role.RoleEntity;
import mx.edu.utez.rbbackcomite.models.role.RoleRepository;
import mx.edu.utez.rbbackcomite.models.user.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServices {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final GroupRepository groupRepository;
  //  private final PasswordEncoder passwordEncoder;

    public ResponseEntity<ApiResponse> getAll() {
        List<UserEntity> users = repository.findAll();
        return ResponseEntity.ok(new ApiResponse(users, false, "Usuarios encontrados"));
    }

    public ResponseEntity<ApiResponse> getOne(Long id) {
        Optional<UserEntity> found = repository.findById(id);
        if (found.isPresent()) {
            return ResponseEntity.ok(new ApiResponse(found.get(), false, "Usuario encontrado"));
        }
        return ResponseEntity.status(404).body(new ApiResponse(null, true, "Usuario no encontrado"));
    }

    public ResponseEntity<ApiResponse> insert(UserDto dto) {
        // Validar unicidad de username y email
        if (repository.existsByUsername(dto.getUsername())) {
            return ResponseEntity.badRequest().body(new ApiResponse(null, true, "El nombre de usuario ya está en uso"));
        }
        if (repository.existsByEmail(dto.getEmail())) {
            return ResponseEntity.badRequest().body(new ApiResponse(null, true, "El email ya está registrado"));
        }

        // Validar que el rol existe
        Optional<RoleEntity> role = roleRepository.findById(dto.getRoleId());
        if (role.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse(null, true, "Rol no encontrado"));
        }

        // Validar grupo si se proporciona
        Optional<GroupEntity> group = Optional.empty();
        if (dto.getGroupId() != null) {
            group = groupRepository.findById(dto.getGroupId());
            if (group.isEmpty()) {
                return ResponseEntity.badRequest().body(new ApiResponse(null, true, "Grupo no encontrado"));
            }
        }

        // Crear usuario
        UserEntity user = dto.toEntity();
     //   user.setPassword(passwordEncoder.encode(dto.getPassword())); // Encriptar contraseña
        user.setRole(role.get());
        group.ifPresent(user::setGroup);

        UserEntity saved = repository.save(user);
        return ResponseEntity.status(201).body(new ApiResponse(saved, false, "Usuario registrado correctamente"));
    }

    public ResponseEntity<ApiResponse> update(Long id, UserDto dto) {
        Optional<UserEntity> found = repository.findById(id);
        if (found.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse(null, true, "Usuario no encontrado"));
        }

        // Validar que el username no esté en uso por otro usuario
        Optional<UserEntity> byUsername = repository.findByUsername(dto.getUsername());
        if (byUsername.isPresent() && !byUsername.get().getId().equals(id)) {
            return ResponseEntity.badRequest().body(new ApiResponse(null, true, "El nombre de usuario ya está en uso"));
        }

        // Validar que el email no esté en uso por otro usuario
        Optional<UserEntity> byEmail = repository.findByEmail(dto.getEmail());
        if (byEmail.isPresent() && !byEmail.get().getId().equals(id)) {
            return ResponseEntity.badRequest().body(new ApiResponse(null, true, "El email ya está registrado"));
        }

        // Validar que el rol existe
        Optional<RoleEntity> role = roleRepository.findById(dto.getRoleId());
        if (role.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse(null, true, "Rol no encontrado"));
        }

        // Validar grupo si se proporciona
        Optional<GroupEntity> group = Optional.empty();
        if (dto.getGroupId() != null) {
            group = groupRepository.findById(dto.getGroupId());
            if (group.isEmpty()) {
                return ResponseEntity.badRequest().body(new ApiResponse(null, true, "Grupo no encontrado"));
            }
        }

        // Actualizar usuario
        UserEntity user = found.get();
        user.setUsername(dto.getUsername());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
        //    user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        user.setFullName(dto.getFullName());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setRole(role.get());
        group.ifPresentOrElse(
                user::setGroup,
                () -> user.setGroup(null)
        );

        UserEntity updated = repository.save(user);
        return ResponseEntity.ok(new ApiResponse(updated, false, "Usuario actualizado correctamente"));
    }

    public ResponseEntity<ApiResponse> delete(Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(404).body(new ApiResponse(null, true, "Usuario no encontrado"));
        }

        repository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse(null, false, "Usuario eliminado correctamente"));
    }

    public ResponseEntity<ApiResponse> getUsersByGroup(Long groupId) {
        if (!groupRepository.existsById(groupId)) {
            return ResponseEntity.badRequest().body(new ApiResponse(null, true, "Grupo no encontrado"));
        }
        List<UserEntity> users = repository.findByGroupId(groupId);
        return ResponseEntity.ok(new ApiResponse(users, false, "Usuarios del grupo encontrados"));
    }

    public ResponseEntity<ApiResponse> getUsersByRole(Long roleId) {
        if (!roleRepository.existsById(roleId)) {
            return ResponseEntity.badRequest().body(new ApiResponse(null, true, "Rol no encontrado"));
        }
        List<UserEntity> users = repository.findByRoleId(roleId);
        return ResponseEntity.ok(new ApiResponse(users, false, "Usuarios con el rol encontrados"));
    }
}
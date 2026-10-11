package bo.academia.carvic.presentation.user;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import bo.academia.carvic.application.user.*;
import bo.academia.carvic.application.user.dto.*;
import bo.academia.carvic.domain.user.User;
import bo.academia.carvic.presentation.user.dto.UserRequest;
import bo.academia.carvic.presentation.user.dto.UserResponseDto;
import bo.academia.carvic.presentation.user.mapper.UserMapper;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserByAdminUseCase updateUserByAdminUseCase;
    private final UpdateUserPasswordUseCase updateUserPasswordUseCase;
    private final GetUserAclMatrixUseCase getUserAclMatrixUseCase;
    private final UpdateUserAclMatrixUseCase updateUserAclMatrixUseCase;
    private final FindByIdUserUseCase findByIdUserUseCase;
    private final FindAllUserUseCase findAllUserUseCase;
    private final FindByUsernameUserUseCase findByUsernameUserUseCase;
    private final FindByEmailUserUseCase findByEmailUserUseCase;
    private final UserMapper userMapper;

    public UserController(
            CreateUserUseCase createUserUseCase,
            UpdateUserByAdminUseCase updateUserByAdminUseCase,
            UpdateUserPasswordUseCase updateUserPasswordUseCase,
            GetUserAclMatrixUseCase getUserAclMatrixUseCase,
            UpdateUserAclMatrixUseCase updateUserAclMatrixUseCase,
            FindByIdUserUseCase findByIdUserUseCase,
            FindAllUserUseCase findAllUserUseCase,
            FindByUsernameUserUseCase findByUsernameUserUseCase,
            FindByEmailUserUseCase findByEmailUserUseCase,
            @Qualifier("presentationUserMapper") UserMapper userMapper) {
        this.createUserUseCase = createUserUseCase;
        this.updateUserByAdminUseCase = updateUserByAdminUseCase;
        this.updateUserPasswordUseCase = updateUserPasswordUseCase;
        this.getUserAclMatrixUseCase = getUserAclMatrixUseCase;
        this.updateUserAclMatrixUseCase = updateUserAclMatrixUseCase;
        this.findByIdUserUseCase = findByIdUserUseCase;
        this.findAllUserUseCase = findAllUserUseCase;
        this.findByUsernameUserUseCase = findByUsernameUserUseCase;
        this.findByEmailUserUseCase = findByEmailUserUseCase;
        this.userMapper = userMapper;
    }

    // =========================================================================
    // ENDPOINTS DE CREACIÓN Y CONSULTA GENERAL
    // =========================================================================

    @PostMapping
    public UserResponseDto create(@RequestBody UserRequest request) {
        User user = createUserUseCase.execute(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getPermissions(),
                request.getRoleId()
        );
        return userMapper.toResponse(user);
    }

    @GetMapping("/{id}")
    public UserResponseDto findById(@PathVariable UUID id) {
        return userMapper.toResponse(findByIdUserUseCase.execute(id));
    }

    @GetMapping("/username/{username}")
    public UserResponseDto findByUsername(@PathVariable String username) {
        return userMapper.toResponse(findByUsernameUserUseCase.execute(username));
    }

    @GetMapping("/email/{email}")
    public UserResponseDto findByEmail(@PathVariable String email) {
        return userMapper.toResponse(findByEmailUserUseCase.execute(email));
    }

    @GetMapping
    public List<UserResponseDto> findAll() {
        return findAllUserUseCase.execute().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    // =========================================================================
    // ENDPOINTS GESTIÓN ADMINISTRATIVA (PANEL DE USUARIOS)
    // =========================================================================

    @PutMapping("/{id}/profile")
    public UserResponseDto updateProfileByAdmin(
            @PathVariable UUID id, 
            @RequestBody UserRequest request) {
        // Permite cambiar datos básicos, rol e indicador de cookies. 
        // Si envía password, se encripta y se fuerza el requirePasswordChange a true
        User updated = updateUserByAdminUseCase.execute(
                id,
                request.getUsername(),
                request.getEmail(),
                request.getRoleId(),
                request.getRefreshTokenHash(), // Tu flag de control original
                request.getPassword() 
        );
        return userMapper.toResponse(updated);
    }

    // =========================================================================
    // ENDPOINTS MATRIZ ACL (PANTALLA DE MATRIZ DE SELECTS)
    // =========================================================================

    @GetMapping("/{id}/acl-matrix")
    public List<AclMatrixRowResponse> getAclMatrix(@PathVariable UUID id) {
        // Alimenta tu grilla mostrando si cada permiso es ALLOWED, DENIED, o HEREDADO
        return getUserAclMatrixUseCase.execute(id);
    }

    @PutMapping("/{id}/acl-matrix")
    public String saveAclMatrix(
            @PathVariable UUID id, 
            @RequestBody List<UserPermissionRequest> requests) {
        // Guarda de golpe los cambios de la grilla de control de accesos
        updateUserAclMatrixUseCase.execute(id, requests);
        return "Matriz de control de acceso sincronizada de forma exitosa";
    }

    // =========================================================================
    // ENDPOINTS AUTOGESTIÓN DE CONTRASEÑA (USUARIO COMÚN)
    // =========================================================================

    @PatchMapping("/{id}/change-password")
    public String updateMyPassword(
            @PathVariable UUID id,
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmNewPassword) {
        // Flujo tradicional: Valida clave actual, compara las nuevas y desmarca requirePasswordChange
        updateUserPasswordUseCase.execute(id, currentPassword, newPassword, confirmNewPassword);
        return "Tu contraseña ha sido modificada correctamente";
    }
}

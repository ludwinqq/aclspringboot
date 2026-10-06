package bo.academia.carvic.presentation.permission;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bo.academia.carvic.application.permission.CreatePermissionUseCase;
import bo.academia.carvic.application.permission.FindAllPermissionUseCase;
import bo.academia.carvic.application.permission.FindByIdPermissionUseCase;
import bo.academia.carvic.application.permission.FindByNamePermissionUseCase;
import bo.academia.carvic.application.permission.UpdatePermissionUseCase;
import bo.academia.carvic.domain.permission.Permission;
import bo.academia.carvic.presentation.permission.dto.CreatePermissionDto;
import bo.academia.carvic.presentation.permission.dto.ReponsePermissionDto;
import bo.academia.carvic.presentation.permission.dto.UpdatePermissionDto;
import bo.academia.carvic.presentation.permission.mapper.PermissionMapper;
import jakarta.validation.Valid;

@RestController 
@RequestMapping ("/api/permissions")
public class PermissionController {

    private final CreatePermissionUseCase createPermissionUseCase;
    private final UpdatePermissionUseCase updatePermissionUseCase;
    private final FindAllPermissionUseCase findAllPermissionUseCase;
    private final FindByIdPermissionUseCase findByIdPermissionUseCase;
    private final FindByNamePermissionUseCase findByNamePermissionUseCase;
    private final PermissionMapper permissionMapper;
    
    public PermissionController(CreatePermissionUseCase createPermissionUseCase,
            UpdatePermissionUseCase updatePermissionUseCase, FindAllPermissionUseCase findAllPermissionUseCase,
            FindByIdPermissionUseCase findByIdPermissionUseCase,
            FindByNamePermissionUseCase findByNamePermissionUseCase, PermissionMapper permissionMapper) {
        this.createPermissionUseCase = createPermissionUseCase;
        this.updatePermissionUseCase = updatePermissionUseCase;
        this.findAllPermissionUseCase = findAllPermissionUseCase;
        this.findByIdPermissionUseCase = findByIdPermissionUseCase;
        this.findByNamePermissionUseCase = findByNamePermissionUseCase;
        this.permissionMapper = permissionMapper;
    }

    @PostMapping 
    public ReponsePermissionDto create(@Valid @RequestBody CreatePermissionDto request) {
        Permission permission = permissionMapper.toDomain(request);
        Permission create = createPermissionUseCase.execute(permission);
        return permissionMapper.toResponse(create);
    }

    @PutMapping ("/{id}")
    public ReponsePermissionDto update(@PathVariable UUID id, @RequestBody UpdatePermissionDto request) {
        Permission permission = permissionMapper.toDomain(request);
        Permission update = updatePermissionUseCase.execute(id, permission);
        return permissionMapper.toResponse(update);
    }

    @GetMapping 
    public List<ReponsePermissionDto> getAll() {
        return findAllPermissionUseCase.execute().stream().map(permissionMapper::toResponse).toList();
    }

    @GetMapping ("/{id}")
    public ReponsePermissionDto getById(@PathVariable UUID id) {
        return permissionMapper.toResponse(findByIdPermissionUseCase.execute(id));
    }

    @GetMapping ("/name/{name}")
    public ReponsePermissionDto getByName(@PathVariable String name) {
        return permissionMapper.toResponse(findByNamePermissionUseCase.execute(name));
    }
}

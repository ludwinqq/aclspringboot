package bo.academia.carvic.presentation.permission;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bo.academia.carvic.application.permission.CreatePermissionUseCase;
import bo.academia.carvic.application.permission.FindAllPermissionUseCase;
import bo.academia.carvic.application.permission.FindByIdPermissionUseCase;
import bo.academia.carvic.application.permission.FindByNamePermissionUseCase;
import bo.academia.carvic.application.permission.GetPagedPermissionsUseCase;
import bo.academia.carvic.application.permission.UpdatePermissionUseCase;
import bo.academia.carvic.domain.common.helpers.pagination.PageResponse;
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
    private final GetPagedPermissionsUseCase getPagedPermissionsUseCase;
    private final FindByIdPermissionUseCase findByIdPermissionUseCase;
    private final FindByNamePermissionUseCase findByNamePermissionUseCase;
    private final PermissionMapper permissionMapper;
    
    public PermissionController(
        CreatePermissionUseCase createPermissionUseCase,
        UpdatePermissionUseCase updatePermissionUseCase,
        FindAllPermissionUseCase findAllPermissionUseCase,
        FindByIdPermissionUseCase findByIdPermissionUseCase,
        FindByNamePermissionUseCase findByNamePermissionUseCase,
        PermissionMapper permissionMapper,
        GetPagedPermissionsUseCase getPagedPermissionsUseCase) {
        this.createPermissionUseCase = createPermissionUseCase;
        this.updatePermissionUseCase = updatePermissionUseCase;
        this.findAllPermissionUseCase = findAllPermissionUseCase;
        this.findByIdPermissionUseCase = findByIdPermissionUseCase;
        this.findByNamePermissionUseCase = findByNamePermissionUseCase;
        this.permissionMapper = permissionMapper;
        this.getPagedPermissionsUseCase = getPagedPermissionsUseCase;
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
    public PageResponse<ReponsePermissionDto> getAllPaged(
        @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction ) {

        // 1. Ejecutas el caso de uso que te devuelve la página envuelta en entidades de dominio (o DTOs, según prefieras)
        PageResponse<Permission> permissionPage = getPagedPermissionsUseCase.execute(page, size, sortBy, direction);
        // 2. Mapear la lista interna de entidades a PermissionResponseDto manteniendo la paginación
        List<ReponsePermissionDto> responseDtos = permissionPage.getContent().stream()
                    .map(permissionMapper::toResponse)
                    .collect(Collectors.toList());
        // 3. Retornas la respuesta paginada con los DTOs de respuesta
        return new PageResponse<>(
            responseDtos,
            permissionPage.getCurrentPage(),
            permissionPage.getTotalPages(),
            permissionPage.getTotalElements(),
            permissionPage.getPageSize()
        );

    }
    /*@GetMapping
    public PagedResult<PermissionProjection> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return findAllPermissionUseCase.execute(page, size);
    }*/

    @GetMapping ("/{id}")
    public ReponsePermissionDto getById(@PathVariable UUID id) {
        return permissionMapper.toResponse(findByIdPermissionUseCase.execute(id));
    }

    @GetMapping ("/name/{name}")
    public ReponsePermissionDto getByName(@PathVariable String name) {
        return permissionMapper.toResponse(findByNamePermissionUseCase.execute(name));
    }
}

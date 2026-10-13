package bo.academia.carvic.presentation.role;

import bo.academia.carvic.application.role.CreateRoleUseCase;
import bo.academia.carvic.application.role.FindAllRoleUseCase;
import bo.academia.carvic.application.role.FindByNameRoleUseCase;
import bo.academia.carvic.application.role.GetPagedRolesUseCase;
import bo.academia.carvic.application.role.UpdateRoleUseCase;
import bo.academia.carvic.application.role.FindByIdRoleUseCase;
import bo.academia.carvic.domain.common.helpers.pagination.PageResponse;
import bo.academia.carvic.domain.role.Role;
import bo.academia.carvic.presentation.role.dto.RoleRequest;
import bo.academia.carvic.presentation.role.dto.RoleResponseDto;
import bo.academia.carvic.presentation.role.mapper.RoleMapper;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final FindByNameRoleUseCase findByNameRoleUseCase;
     private final CreateRoleUseCase createRoleUseCase;
    private final UpdateRoleUseCase updateRoleUseCase;
    private final FindByIdRoleUseCase findByIdRoleUseCase;
    private final RoleMapper roleMapper;
    private final GetPagedRolesUseCase getPagedRolesUseCase;

    public RoleController(
            CreateRoleUseCase createRoleUseCase, 
            UpdateRoleUseCase updateRoleUseCase, 
            FindByIdRoleUseCase findByIdRoleUseCase, 
            FindAllRoleUseCase findAllRoleUseCase, 
            @Qualifier("presentationRoleMapper") RoleMapper roleMapper, 
            FindByNameRoleUseCase findByNameRoleUseCase, 
            GetPagedRolesUseCase getPagedRolesUseCase) {
        this.createRoleUseCase = createRoleUseCase;
        this.updateRoleUseCase = updateRoleUseCase;
        this.findByIdRoleUseCase = findByIdRoleUseCase;
        this.roleMapper = roleMapper;
        this.findByNameRoleUseCase = findByNameRoleUseCase;
        this.getPagedRolesUseCase = getPagedRolesUseCase;
    }

    @GetMapping
    public PageResponse<RoleResponseDto> getAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        
        // 1. Ejecutas el caso de uso que te devuelve la página envuelta en entidades de dominio (o DTOs, según prefieras)
        PageResponse<Role> rolePage = getPagedRolesUseCase.execute(page, size, sortBy, direction);
        
        // 2. Mapeas la lista interna de entidades a RoleResponseDto manteniendo la paginación
        List<RoleResponseDto> responseDtos = rolePage.getContent().stream()
                .map(roleMapper::toResponse)
                .collect(Collectors.toList());

        // 3. Retornas la respuesta paginada con los DTOs de respuesta
        return new PageResponse<>(
                responseDtos,
                rolePage.getCurrentPage(),
                rolePage.getTotalPages(),
                rolePage.getTotalElements(),
                rolePage.getPageSize()
        );
    }

    @PostMapping
    public RoleResponseDto create(@RequestBody RoleRequest request) {
        Role role = createRoleUseCase.execute(
                request.getName(), 
                request.getDescription(), 
                request.getPermissions()
        );
        return roleMapper.toResponse(role);
    }

    @PutMapping("/{id}")
    public RoleResponseDto update(@PathVariable UUID id, @RequestBody RoleRequest request) {
        Role updatedRole = updateRoleUseCase.execute(
                id, 
                request.getName(), 
                request.getDescription(), 
                request.getPermissions()
        );
        return roleMapper.toResponse(updatedRole);
    }

    @GetMapping("/{id}")
    public RoleResponseDto findById(@PathVariable UUID id) {
        return roleMapper.toResponse(findByIdRoleUseCase.execute(id));
    }

    @GetMapping ("/name/{name}")
    public RoleResponseDto findByName(@PathVariable String name) {
        return roleMapper.toResponse(findByNameRoleUseCase.execute(name));
    }

    /*@GetMapping 
    public List<RoleResponseDto> findAll() {
        return findAllRoleUseCase.execute().stream().map(roleMapper::toResponse).toList();
    }*/
}

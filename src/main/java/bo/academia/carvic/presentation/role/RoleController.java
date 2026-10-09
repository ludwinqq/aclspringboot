package bo.academia.carvic.presentation.role;

import bo.academia.carvic.application.role.CreateRoleUseCase;
import bo.academia.carvic.application.role.FindAllRoleUseCase;
import bo.academia.carvic.application.role.FindByNameRoleUseCase;
import bo.academia.carvic.application.role.UpdateRoleUseCase;
import bo.academia.carvic.application.role.FindByIdRoleUseCase;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final FindByNameRoleUseCase findByNameRoleUseCase;
    private final FindAllRoleUseCase findAllRoleUseCase;
    private final CreateRoleUseCase createRoleUseCase;
    private final UpdateRoleUseCase updateRoleUseCase;
    private final FindByIdRoleUseCase findByIdRoleUseCase;
    private final RoleMapper roleMapper;

    public RoleController(
            CreateRoleUseCase createRoleUseCase, 
            UpdateRoleUseCase updateRoleUseCase, 
            FindByIdRoleUseCase findByIdRoleUseCase, FindAllRoleUseCase findAllRoleUseCase, @Qualifier("presentationRoleMapper") RoleMapper roleMapper, FindByNameRoleUseCase findByNameRoleUseCase) {
        this.createRoleUseCase = createRoleUseCase;
        this.updateRoleUseCase = updateRoleUseCase;
        this.findByIdRoleUseCase = findByIdRoleUseCase;
        this.findAllRoleUseCase = findAllRoleUseCase;
        this.roleMapper = roleMapper;
        this.findByNameRoleUseCase = findByNameRoleUseCase;
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

    @GetMapping 
    public List<RoleResponseDto> findAll() {
        return findAllRoleUseCase.execute().stream().map(roleMapper::toResponse).toList();
    }
}

package bo.academia.carvic.presentation.role;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bo.academia.carvic.application.role.CreateRoleUseCase;
import bo.academia.carvic.application.role.FindAllRoleUseCase;
import bo.academia.carvic.application.role.FindByIdRoleUseCase;
import bo.academia.carvic.application.role.FindByNameRoleUseCase;
import bo.academia.carvic.application.role.UpdateRoleUseCase;
import bo.academia.carvic.domain.role.Role;
import bo.academia.carvic.presentation.role.dto.CreateRoleRequestDto;
import bo.academia.carvic.presentation.role.dto.RoleResponseDto;
import jakarta.validation.Valid;

@RestController 
@RequestMapping ("/api/roles")
public class RoleController {

    private final CreateRoleUseCase createRoleUseCase;
    private final FindAllRoleUseCase findAllRoleUseCase;
    private final FindByIdRoleUseCase findByIdRoleUseCase;
    private final FindByNameRoleUseCase findByNameRoleUseCase;
    private final UpdateRoleUseCase updateRoleUseCase;
    
    public RoleController(CreateRoleUseCase createRoleUseCase, FindAllRoleUseCase findAllRoleUseCase,
            FindByIdRoleUseCase findByIdRoleUseCase, FindByNameRoleUseCase findByNameRoleUseCase,
            UpdateRoleUseCase updateRoleUseCase) {
        this.createRoleUseCase = createRoleUseCase;
        this.findAllRoleUseCase = findAllRoleUseCase;
        this.findByIdRoleUseCase = findByIdRoleUseCase;
        this.findByNameRoleUseCase = findByNameRoleUseCase;
        this.updateRoleUseCase = updateRoleUseCase;
    }

    @PostMapping
    public ResponseEntity<RoleResponseDto> create(@Valid @RequestBody CreateRoleRequestDto requestDto) {
        Role role = new Role();
        role.setName(requestDto.getName());
        role.setDescription(requestDto.getDescription());

        Role saved = createRoleUseCase.execute(role);

        RoleResponseDto responseDto = mapToResponse(saved);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping 
    public ResponseEntity<List<RoleResponseDto>> getAll() {
        List<RoleResponseDto> responseDto = findAllRoleUseCase.execute().stream().map(e -> new RoleResponseDto(
            e.getId(),
            e.getName(),
            e.getDescription(),
            e.getStatus()
        )).toList();

        return ResponseEntity.ok(responseDto);
    }

    private RoleResponseDto mapToResponse(Role role) {
        return new RoleResponseDto(
            role.getId(),
            role.getName(),
            role.getDescription(),
            role.getStatus()
        );
    }
}

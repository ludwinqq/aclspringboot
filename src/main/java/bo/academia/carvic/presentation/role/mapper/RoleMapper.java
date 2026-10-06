package bo.academia.carvic.presentation.role.mapper;

import org.springframework.stereotype.Component;

import bo.academia.carvic.domain.role.Role;
import bo.academia.carvic.presentation.role.dto.CreateRoleRequestDto;
import bo.academia.carvic.presentation.role.dto.RoleResponseDto;
import bo.academia.carvic.presentation.role.dto.UpdateRoleRequestDto;

@Component ("presentationRoleMapper")
public class RoleMapper {

    // Mapea DTO de Creación a Dominio
    public Role toDomain(CreateRoleRequestDto request) {
        if (request == null) return null;
        Role role = new Role();
        role.setName(request.getName());
        role.setDescription(request.getDescription());
        return role;
    }

    // Mapea DTO de Actualización a Dominio
    public Role toDomain(UpdateRoleRequestDto request) {
        if (request == null) return null;
        Role role = new Role();
        role.setName(request.getName());
        role.setDescription(request.getDescription());
        return role;
    }

    // Mapea de Dominio a DTO de Respuesta
    public RoleResponseDto toResponse(Role role) {
        if (role == null) return null;
        return new RoleResponseDto(
            role.getId(),
            role.getName(),
            role.getDescription(),
            role.getStatus(),
            role.getCreatedAt(),
            role.getUpdatedAt()
        );
    }
}
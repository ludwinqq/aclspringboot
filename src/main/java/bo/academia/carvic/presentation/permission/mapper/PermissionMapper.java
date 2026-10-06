package bo.academia.carvic.presentation.permission.mapper;

import org.springframework.stereotype.Component;

import bo.academia.carvic.domain.permission.Permission;
import bo.academia.carvic.presentation.permission.dto.CreatePermissionDto;
import bo.academia.carvic.presentation.permission.dto.ReponsePermissionDto;
import bo.academia.carvic.presentation.permission.dto.UpdatePermissionDto;

@Component 
public class PermissionMapper {

    public Permission toDomain(CreatePermissionDto request) {
        if ( request == null ) return null;
        Permission permission = new Permission();
        permission.setName(request.getName());
        permission.setTitle(request.getTitle());
        permission.setDescription(request.getDescription());
        permission.setModule(request.getModule());
        return permission;
    }

    public Permission toDomain(UpdatePermissionDto request) {
        if ( request == null ) return null;
        Permission permission = new Permission();
        permission.setName(request.getName());
        permission.setTitle(request.getTitle());
        permission.setDescription(request.getDescription());
        permission.setModule(request.getModule());
        return permission;
    }

    public ReponsePermissionDto toResponse(Permission permission) {
        if ( permission == null ) return null;
        return new ReponsePermissionDto(
            permission.getId(),
            permission.getName(),
            permission.getTitle(),
            permission.getDescription(),
            permission.getModule(),
            permission.getStatus(),
            permission.getCreatedAt(),
            permission.getUpdatedAt()
        );
    }
}

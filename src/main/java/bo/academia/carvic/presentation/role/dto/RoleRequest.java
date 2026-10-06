package bo.academia.carvic.presentation.role.dto;

import bo.academia.carvic.application.role.dto.RolePermissionRequest;
import java.util.List;

public class RoleRequest {
    private String name;
    private String description;
    private List<RolePermissionRequest> permissions; // Reutiliza el DTO de aplicación

    // Getters y Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<RolePermissionRequest> getPermissions() { return permissions; }
    public void setPermissions(List<RolePermissionRequest> permissions) { this.permissions = permissions; }
}

package bo.academia.carvic.application.role.dto;

import java.util.UUID;

public class RolePermissionRequest {
    private UUID permissionId;
    private Boolean permitted;

    // Getters y Setters
    public UUID getPermissionId() { return permissionId; }
    public void setPermissionId(UUID permissionId) { this.permissionId = permissionId; }
    public Boolean getPermitted() { return permitted; }
    public void setPermitted(Boolean permitted) { this.permitted = permitted; }

}

package bo.academia.carvic.application.user.dto;

import java.util.UUID;

public class UserPermissionRequest {
    private UUID permissionId;
    private Boolean permitted;

    public UUID getPermissionId() { return permissionId; }
    public void setPermissionId(UUID permissionId) { this.permissionId = permissionId; }
    public Boolean getPermitted() { return permitted; }
    public void setPermitted(Boolean permitted) { this.permitted = permitted; }
}


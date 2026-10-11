package bo.academia.carvic.application.user.dto;

import java.util.UUID;

import bo.academia.carvic.domain.user.AclStrategy;

public class UserPermissionRequest {
    private UUID permissionId;
    //private Boolean permitted;
    private AclStrategy strategy;
    
    public UUID getPermissionId() {
        return permissionId;
    }
    public void setPermissionId(UUID permissionId) {
        this.permissionId = permissionId;
    }
    public AclStrategy getStrategy() {
        return strategy;
    }
    public void setStrategy(AclStrategy strategy) {
        this.strategy = strategy;
    }
    
}


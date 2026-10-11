package bo.academia.carvic.application.user.dto;

import java.util.UUID;

import bo.academia.carvic.domain.user.AclStrategy;

public class AclMatrixRowResponse {

    private UUID permissionId;
    private String permissionName;
    private String module;
    private AclStrategy strategy;
    
    public AclMatrixRowResponse(UUID permissionId, String permissionName, String module, AclStrategy strategy) {
        this.permissionId = permissionId;
        this.permissionName = permissionName;
        this.module = module;
        this.strategy = strategy;
    }

    public UUID getPermissionId() {
        return permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public String getModule() {
        return module;
    }

    public AclStrategy getStrategy() {
        return strategy;
    }
    
    
    
}

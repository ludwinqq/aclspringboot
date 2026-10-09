package bo.academia.carvic.domain.role;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Role {

    private UUID id;
    private String name;
    private String description;
    private Integer status = 1;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<RolePermissionRule> permissionRules = new ArrayList<>();

    public Role() {
    }

    public Role(String name, String description, List<RolePermissionRule> permissionRules) {

        if ( name == null || name.isBlank() ) {
            throw new IllegalArgumentException("Nombre es requerido");
        }
        
        this.name = name;
        this.description = description;
        
        if (permissionRules != null ) {
            this.permissionRules = permissionRules;
        }
        
    }

    public void updateDetails(String name, String description) {
        if ( name != null && !name.isBlank() ) {
            this.name = name;
        }
        if ( description != null ) {
            this.description = description;
        }
    }

    public void updatePermission(List<RolePermissionRule> newRules) {
        this.permissionRules = newRules != null ? newRules : new ArrayList<>();
    }

    public boolean hasAccess(String permissionName) {
        return this.permissionRules.stream()
            .filter(rule -> rule.getPermission().getName().equals(permissionName))
            .findFirst()
            .map(RolePermissionRule::isPermitted)
            .orElse(false);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<RolePermissionRule> getPermissionRules() {
        return permissionRules;
    }

    public void setPermissionRules(List<RolePermissionRule> permissionRules) {
        this.permissionRules = permissionRules;
    }
    
}

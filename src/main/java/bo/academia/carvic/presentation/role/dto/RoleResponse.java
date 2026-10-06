package bo.academia.carvic.presentation.role.dto;

import bo.academia.carvic.domain.role.Role;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RoleResponse {
    private UUID id;
    private String name;
    private String description;
    private Integer status;
    private List<PermissionRuleDto> permissions;

    public RoleResponse(Role role) {
        this.id = role.getId();
        this.name = role.getName();
        this.description = role.getDescription();
        this.status = role.getStatus();
        this.permissions = role.getPermissionRules().stream()
                .map(rule -> new PermissionRuleDto(
                        rule.getPermission().getId(),
                        rule.getPermission().getName(),
                        rule.isPermitted()
                ))
                .collect(Collectors.toList());
    }

    // Getters...
    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Integer getStatus() { return status; }
    public List<PermissionRuleDto> getPermissions() { return permissions; }

    // Sub-DTO interno estático para los permisos en la respuesta
    public static class PermissionRuleDto {
        private UUID permissionId;
        private String permissionName;
        private Boolean permitted;

        public PermissionRuleDto(UUID permissionId, String permissionName, Boolean permitted) {
            this.permissionId = permissionId;
            this.permissionName = permissionName;
            this.permitted = permitted;
        }
        // Getters...
        public UUID getPermissionId() { return permissionId; }
        public String getPermissionName() { return permissionName; }
        public Boolean getPermitted() { return permitted; }
    }
}

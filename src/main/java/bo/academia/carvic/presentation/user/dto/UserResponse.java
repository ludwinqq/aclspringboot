package bo.academia.carvic.presentation.user.dto;

import java.util.List;
import java.util.UUID;

public class UserResponse {
    private UUID id;
    private String username;
    private String email;
    private UUID roleId;
    private List<CustomRuleDto> customPermissions;

    public UserResponse(UUID id, String username, String email, UUID roleId, List<CustomRuleDto> customPermissions) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roleId = roleId;
        this.customPermissions = customPermissions;
    }

    // Getters...
    public UUID getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public UUID getRoleId() { return roleId; }
    public List<CustomRuleDto> getCustomPermissions() { return customPermissions; }

    public static class CustomRuleDto {
        private UUID permissionId;
        private String permissionName;
        private Boolean permitted;

        public CustomRuleDto(UUID permissionId, String permissionName, Boolean permitted) {
            this.permissionId = permissionId;
            this.permissionName = permissionName;
            this.permitted = permitted;
        }
        public UUID getPermissionId() { return permissionId; }
        public String getPermissionName() { return permissionName; }
        public Boolean getPermitted() { return permitted; }
    }
}

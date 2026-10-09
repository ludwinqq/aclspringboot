package bo.academia.carvic.presentation.user.dto;

import java.util.List;
import java.util.UUID;
import bo.academia.carvic.application.user.dto.UserPermissionRequest;

public class UserRequest {
    private String username;
    private String email;
    private String password;
    private UUID roleId;
    private List<UserPermissionRequest> permissions;

    // Getters y Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public UUID getRoleId() { return roleId; }
    public void setRoleId(UUID roleId) { this.roleId = roleId; }
    public List<UserPermissionRequest> getPermissions() { return permissions; }
    public void setPermissions(List<UserPermissionRequest> permissions) { this.permissions = permissions; }
}

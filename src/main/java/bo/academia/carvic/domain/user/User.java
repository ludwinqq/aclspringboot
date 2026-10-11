package bo.academia.carvic.domain.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {

    private UUID id;
    private String username;
    private String email;
    private String password;
    private String refreshTokenHash; // <-- Mantenemos tu indicador original intacto
    private Boolean requirePasswordChange = true;
    private UUID roleId;
    private Integer status = 1;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<UserPermissionRule> permissionRules = new ArrayList<>();
    
    public User() {
    }

    // Constructor completo para Persistencia/Mappers
    public User(UUID id, String username, String email, String password, String refreshTokenHash,
                Boolean requirePasswordChange, UUID roleId, List<UserPermissionRule> permissionRules) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.refreshTokenHash = refreshTokenHash;
        this.requirePasswordChange = requirePasswordChange;
        this.roleId = roleId;
        if (permissionRules != null) {
            this.permissionRules = permissionRules;
        }
    }

    // Lógica de negocio administrativa: Cambiar datos de perfil
    public void changeProfileByAdmin(String username, String email, UUID roleId, String refreshTokenHash) {
        this.username = username;
        this.email = email;
        this.roleId = roleId;
        this.refreshTokenHash = refreshTokenHash;
    }

    // Lógica de negocio: Cambiar contraseña
    public void updatePassword(String encryptedPassword, boolean mustChangeOnNextLogin) {
        if (encryptedPassword == null || encryptedPassword.isBlank()) {
            throw new IllegalArgumentException("La contraseña es requerida");
        }
        this.password = encryptedPassword;
        this.requirePasswordChange = mustChangeOnNextLogin;
    }

    public void updatePermission(List<UserPermissionRule> newRules) {
        this.permissionRules = newRules != null ? newRules : new ArrayList<>();
    }

    // Regla de Negocio: Reemplazo masivo de la matriz del ACL
    public void replaceAclRules(List<UserPermissionRule> newRules) {
        this.permissionRules = newRules != null ? newRules : new ArrayList<>();
    }

    // El motor jerárquico del ACL
    public boolean hasAccess(String permissionName, boolean roleHasIt) {
        return this.permissionRules.stream()
                .filter(rule -> rule.getPermission().getName().equals(permissionName))
                .findFirst()
                .map(rule -> rule.getStrategy() == AclStrategy.ALLOWED)
                .orElse(roleHasIt); // Si no hay excepción, hereda estrictamente lo calculado del rol
    }

    // Getters y Setters...
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRefreshTokenHash() { return refreshTokenHash; }
    public void setRefreshTokenHash(String refreshTokenHash) { this.refreshTokenHash = refreshTokenHash; }
    public Boolean getRequirePasswordChange() { return requirePasswordChange; }
    public void setRequirePasswordChange(Boolean requirePasswordChange) { this.requirePasswordChange = requirePasswordChange; }
    public UUID getRoleId() { return roleId; }
    public void setRoleId(UUID roleId) { this.roleId = roleId; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public List<UserPermissionRule> getPermissionRules() { return permissionRules; }
    public void setPermissionRules(List<UserPermissionRule> permissionRules) {
        this.permissionRules = permissionRules;
    }
}

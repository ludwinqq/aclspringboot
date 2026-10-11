package bo.academia.carvic.presentation.user.dto;

import java.util.List;
import java.util.UUID;
import bo.academia.carvic.domain.user.AclStrategy;

public class UserResponseDto {
    private UUID id;
    private String username;
    private String email;
    private String refreshTokenHash; // Indicador de control para refresco de JWT
    private UUID roleId;
    private Integer status;
    private List<CustomRuleDto> customPermissions;

    public UserResponseDto() {}

    // CORREGIDO: Se añadió refreshTokenHash al constructor principal para que el Mapper pueda mapearlo
    public UserResponseDto(UUID id, String username, String email, String refreshTokenHash, UUID roleId, Integer status, List<CustomRuleDto> customPermissions) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.refreshTokenHash = refreshTokenHash;
        this.roleId = roleId;
        this.status = status;
        this.customPermissions = customPermissions;
    }

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRefreshTokenHash() { return refreshTokenHash; }
    public void setRefreshTokenHash(String refreshTokenHash) { this.refreshTokenHash = refreshTokenHash; }
    public UUID getRoleId() { return roleId; }
    public void setRoleId(UUID roleId) { this.roleId = roleId; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public List<CustomRuleDto> getCustomPermissions() { return customPermissions; }
    public void setCustomPermissions(List<CustomRuleDto> customPermissions) { this.customPermissions = customPermissions; }

    // Clase interna para las excepciones directas mapeada con la nueva estrategia de estados
    public static class CustomRuleDto {
        private UUID permissionId;
        private String permissionName;
        private AclStrategy strategy; // <-- CORREGIDO: Cambiado de Boolean permitted a AclStrategy (Enum)

        // Constructor corregido
        public CustomRuleDto(UUID permissionId, String permissionName, AclStrategy strategy) {
            this.permissionId = permissionId;
            this.permissionName = permissionName;
            this.strategy = strategy;
        }

        // Getters y Setters corregidos
        public UUID getPermissionId() { return permissionId; }
        public void setPermissionId(UUID permissionId) { this.permissionId = permissionId; }
        public String getPermissionName() { return permissionName; }
        public void setPermissionName(String permissionName) { this.permissionName = permissionName; }
        public AclStrategy getStrategy() { return strategy; }
        public void setStrategy(AclStrategy strategy) { this.strategy = strategy; }
    }
}

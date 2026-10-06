package bo.academia.carvic.presentation.user.dto;

import java.time.LocalDateTime;
import java.util.UUID;


public class UserResponseDto {

    private UUID id;
    private String username;
    private String email;
    private Integer status;
    private UUID roleId;
    //private RoleResponseDto role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public UserResponseDto() {
    }

    public UserResponseDto(UUID id, String username, String email, Integer status, UUID roleId, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.status = status;
        this.roleId = roleId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}

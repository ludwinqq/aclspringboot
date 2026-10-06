package bo.academia.carvic.presentation.user.dto;

import java.util.UUID;

import jakarta.validation.constraints.Email;

public class UpdateUserRequestDto {

    @Email(message = "El formato del correo electrónico no es válido")
    private String email;

    private String password;
    private UUID roleId;
    private Integer status;
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public UUID getRoleId() {
        return roleId;
    }
    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    
}

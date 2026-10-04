package bo.academia.carvic.presentation.user.dto;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateUserRequestDto {

    @NotBlank(message = "El nombre de usuario es requerido")
    private String username;

    @NotBlank(message = "El correo electrónico es requerido")
    @Email(message = "El formato del correo electrónico no es válido")
    private String email;

    @NotBlank(message = "La contraseña es requerida")
    private String password;

    @NotNull(message = "El ID del rol es requerido")
    private UUID roleId;

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
    
}

package bo.academia.carvic.presentation.permission.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdatePermissionDto {

    @NotBlank(message = "El nombre es requerido")
    @Size(max = 50)
    private String name;

    @NotBlank(message = "El título es requerido")
    private String title;

    private String description;

    @NotBlank(message = "El módulo es requerido")
    private String module;

    public UpdatePermissionDto() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getModule() {
        return module;
    }

    
}

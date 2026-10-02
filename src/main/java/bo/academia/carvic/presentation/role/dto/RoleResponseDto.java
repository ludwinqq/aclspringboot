package bo.academia.carvic.presentation.role.dto;

//import java.time.LocalDateTime;
import java.util.UUID;

public class RoleResponseDto {

    private UUID id;
    private String name;
    private String description;
    private Integer status;
    //private LocalDateTime createdAt;

    public RoleResponseDto() {
    }

    public RoleResponseDto(UUID id, String name, String description, Integer status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        //this.createdAt = createdAt;
    }

    // Getters y Setters
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

    /*public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }*/
}
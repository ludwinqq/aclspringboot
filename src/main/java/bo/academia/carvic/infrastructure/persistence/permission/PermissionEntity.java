package bo.academia.carvic.infrastructure.persistence.permission;

import java.util.UUID;

import bo.academia.carvic.infrastructure.persistence.BaseAuditableData;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table (name = "permissions")
public class PermissionEntity extends BaseAuditableData {

    @Id 
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    @Column (name = "name", nullable = false)
    private String name;

    @Column (name = "title", nullable = false)
    private String title;

    @Column (name = "description")
    private String description;

    @Column (name = "module", nullable = false)
    private String module;

    public PermissionEntity() {
    }

    public PermissionEntity(UUID id, String name, String title, String description, String module) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.description = description;
        this.module = module;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    
}

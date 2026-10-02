package bo.academia.carvic.infrastructure.persistence;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass // <-- Indica a JPA que herede estos campos a las tablas
public abstract class BaseAuditableData {

    @Column(name = "status", nullable = false)
    private Integer status = 1;

    @CreationTimestamp // <-- Hibernate asigna la fecha de creación automáticamente
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp // <-- Hibernate actualiza la fecha automáticamente al editar
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

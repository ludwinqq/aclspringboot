package bo.academia.carvic.domain;

import java.time.LocalDateTime;

public abstract class BaseAuditableEntity {

    private Integer status = 1;
    private LocalDateTime createdAt;
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

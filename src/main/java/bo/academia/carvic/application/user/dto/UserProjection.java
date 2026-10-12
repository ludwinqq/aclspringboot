package bo.academia.carvic.application.user.dto;
import java.util.UUID;

public interface UserProjection {
    UUID getId();
    String getUsername();
    String getEmail();
    Integer getStatus();
    String getRoleName(); // Spring mapeará automáticamente r.name a 'getRoleName' si el alias en el query coincide
}

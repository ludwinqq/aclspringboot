package bo.academia.carvic.infrastructure.common.libraries;

// Aquí los imports de Spring son 100% válidos y obligatorios
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import bo.academia.carvic.domain.common.PagedResult;
import java.util.Collections;

@Component("paginationLibrary")
public class PaginationLibrary {

    // Constructor vacío por defecto
    public PaginationLibrary() {
    }

    /**
     * Traduce el Page nativo de Spring al PagedResult puro de tu dominio.
     */
    public <T> PagedResult<T> toPagedResult(Page<T> springPage) {
        if (springPage == null) {
            return new PagedResult<>(Collections.emptyList(), 0, 0, 0, 0, true);
        }

        // Extrae los metadatos de Spring y construye tu objeto de dominio
        return new PagedResult<>(
                springPage.getContent(),
                springPage.getNumber(),
                springPage.getSize(),
                springPage.getTotalElements(),
                springPage.getTotalPages(),
                springPage.isLast()
        );
    }
}

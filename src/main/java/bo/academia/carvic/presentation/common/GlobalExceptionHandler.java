package bo.academia.carvic.presentation.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 1. Intercepta excepciones de validación de negocio (IllegalArgumentException)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        
        // Usamos tu método estático nativo .error(message, code)
        ApiResponse<Void> errorResponse = ApiResponse.error(ex.getMessage(), "400 BAD_REQUEST");
        
        return ResponseEntity.status(status).body(errorResponse);
    }

    /**
     * 2. Intercepta UUIDs mal escritos o parámetros corruptos enviados desde la URL en Postman
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        
        String message = "El ID proporcionado no cumple con el formato estándar de un UUID de 36 caracteres.";
        ApiResponse<Void> errorResponse = ApiResponse.error(message, "400 BAD_REQUEST");
        
        return ResponseEntity.status(status).body(errorResponse);
    }

    /**
     * 3. Intercepta colapsos inesperados o caídas críticas del servidor (Errores 500 reales)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleAllUncaughtExceptions(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        
        String message = "Ha ocurrido un error interno en el servidor, por favor contacte al administrador.";
        ApiResponse<Void> errorResponse = ApiResponse.error(message, "500 INTERNAL_SERVER_ERROR");
        
        ex.printStackTrace(); // Mantiene el log de errores en la consola de Spring para depurar
        return ResponseEntity.status(status).body(errorResponse);
    }
}

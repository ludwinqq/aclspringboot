package bo.academia.carvic.presentation.common;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = "bo.academia.carvic.presentation") // Aplica solo a tus controladores
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // Si el método ya devuelve un ApiResponse o un ResponseEntity, no lo envuelvas de nuevo
        return !returnType.getParameterType().equals(ApiResponse.class)
                && !returnType.getParameterType().equals(org.springframework.http.ResponseEntity.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        
        // Si el controlador lanza una excepción manejada que ya devuelve un ApiResponse, se respeta
        if (body instanceof ApiResponse) {
            return body;
        }

        // Envuelve automáticamente cualquier objeto o lista en tu formato estándar
        return ApiResponse.success("Operación exitosa", body);
    }
}
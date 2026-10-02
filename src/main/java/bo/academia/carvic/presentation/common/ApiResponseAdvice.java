package bo.academia.carvic.presentation.common;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // Evita volver a envolver si ya es un ApiResponse o un ResponseEntity
        return true; 
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedContentTypeHeader,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        
        // Si ya viene envuelto, no hacemos nada
        if (body instanceof ApiResponse) {
            return body;
        }

        // Obtenemos el código HTTP actual (ej. 200, 201) desde la respuesta del servidor
        int statusCode = ((ServletServerHttpResponse) response).getServletResponse().getStatus();
        String codeDescription = statusCode + " " + getStatusDescription(statusCode);

        // Devolvemos tu estructura con el código integrado
        return ApiResponse.success("Operación exitosa", codeDescription, body);
    }

    private String getStatusDescription(int statusCode) {
        if (statusCode == 200) return "OK";
        if (statusCode == 201) return "Created";
        return "SUCCESS";
    }
}
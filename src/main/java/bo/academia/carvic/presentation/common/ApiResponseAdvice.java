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
        // CORREGIDO: Si el método del controlador ya declara que devuelve un ApiResponse, no lo interceptes
        return !returnType.getParameterType().isAssignableFrom(ApiResponse.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedContentTypeHeader,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        
        // CORREGIDO: Validación doble infalible. Si el cuerpo ya es un ApiResponse, lo deja pasar intacto
        if (body instanceof ApiResponse) {
            return body;
        }

        // Obtener el código HTTP real configurado en el Servlet
        int statusCode = ((ServletServerHttpResponse) response).getServletResponse().getStatus();
        
        // CORREGIDO: Si el código es un error (400 o superior) y el cuerpo ya contiene datos estructurados, 
        // significa que viene del GlobalExceptionHandler. ¡No debemos volver a envolverlo en un success!
        if (statusCode >= 400) {
            return body; 
        }

        String codeDescription = statusCode + " " + getStatusDescription(statusCode);

        // Devolvemos tu estructura con el código integrado para flujos exitosos (200, 201)
        return ApiResponse.success("Operación exitosa", codeDescription, body);
    }

    private String getStatusDescription(int statusCode) {
        if (statusCode == 200) return "OK";
        if (statusCode == 201) return "Created";
        return "SUCCESS";
    }
}

package notas.ServidorModule.dao.errores;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import notas.CommonModule.errores.ApiError;

@Provider
public class BaseDatosCaidaExceptionMapper implements ExceptionMapper<BaseDatosCaidaException> {

    public Response toResponse(BaseDatosCaidaException exception) {
        ApiError apiError = new ApiError(exception.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(apiError)
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}

package notas.ServidorModule.dao.errores;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import notas.CommonModule.errores.ApiError;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    public Response toResponse(NotFoundException exception) {
        ApiError apiError = new ApiError(exception.getMessage());
        return Response.status(Response.Status.NOT_FOUND).entity(apiError)
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}

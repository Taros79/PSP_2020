package notas.Servidor.dao.errores;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import notas.Common.errores.ApiError;

@Provider
public class DataViolationExceptionMapper implements ExceptionMapper<DataViolationException> {
    @Override
    public Response toResponse(DataViolationException e) {
        ApiError apiError = new ApiError(e.getMessage());
        return Response.status(Response.Status.CONFLICT).entity(apiError)
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}

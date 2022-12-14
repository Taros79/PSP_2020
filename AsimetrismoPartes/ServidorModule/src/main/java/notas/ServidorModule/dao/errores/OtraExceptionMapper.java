package notas.ServidorModule.dao.errores;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import notas.CommonModule.errores.ApiError;

@Provider
public class OtraExceptionMapper implements ExceptionMapper<OtraException> {
    @Override
    public Response toResponse(OtraException e) {
        ApiError apiError = new ApiError(e.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(apiError)
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}

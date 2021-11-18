package EE.filtros;

import EE.errores.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;


@Provider
@Filtered
//@PreMatching
public class ServerFilterJAX implements ContainerRequestFilter {


  @Context
  private HttpServletRequest httpServletRequest;

  @Override
  public void filter(ContainerRequestContext containerRequestContext) {
    if (httpServletRequest.getSession().getAttribute("kk") == null) {
      //httpServletRequest.getSession().setAttribute("kk",1);
      containerRequestContext.abortWith(Response.status(Response.Status.BAD_REQUEST)
              .entity(ApiError.builder().message("error de filtro").fecha(LocalDateTime.now()).build())
              .type(MediaType.APPLICATION_JSON_TYPE).build());
    }
  }
}

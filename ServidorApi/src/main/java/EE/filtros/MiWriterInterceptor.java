package EE.filtros;

import dao.modelo.UsuarioGetDTO;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.ext.WriterInterceptor;
import jakarta.ws.rs.ext.WriterInterceptorContext;

import java.io.IOException;

@Provider
@Writer
public class MiWriterInterceptor implements WriterInterceptor {

    @Inject
    private Jsonb json;

    @Override
    public void aroundWriteTo(WriterInterceptorContext context)
            throws IOException, WebApplicationException {
        Object entity = context.getEntity();

        if (entity instanceof UsuarioGetDTO) {
            UsuarioGetDTO u = (UsuarioGetDTO) context.getEntity();
            u.setName("secreto");
            context.setEntity(u);
//                .write((json.toJson(u)).getBytes());
        }
        context.proceed();
    }
}

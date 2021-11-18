package EE.filtros;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.ext.ReaderInterceptor;
import jakarta.ws.rs.ext.ReaderInterceptorContext;

import java.io.*;
import java.util.stream.Collectors;

@Provider
@Reader
public class RequestReaderString implements ReaderInterceptor {
    @Override
    public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException {
        InputStream is = context.getInputStream();
        String body = new BufferedReader(new InputStreamReader(is)).lines()
                .collect(Collectors.joining("\n"));

        context.setInputStream(new ByteArrayInputStream(
                ("{\"name\":\"lolo\"}").getBytes()));

        return context.proceed();
    }
}

package EE.errores;

import lombok.Data;

import javax.ws.rs.core.Response;

@Data
public class CustomException extends RuntimeException {

    public CustomException(String message, Response.Status codigo) {
        super(message);
        this.codigo = codigo;
    }

    private Response.Status codigo;

}

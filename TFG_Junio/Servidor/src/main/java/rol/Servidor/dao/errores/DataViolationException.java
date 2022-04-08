package rol.Servidor.dao.errores;


public class DataViolationException extends RuntimeException {


    public DataViolationException(String error) {
        super(error);
    }
}

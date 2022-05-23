package notas.ServidorModule.dao.errores;


public class DataViolationException extends RuntimeException {


    public DataViolationException(String error) {
        super(error);
    }
}

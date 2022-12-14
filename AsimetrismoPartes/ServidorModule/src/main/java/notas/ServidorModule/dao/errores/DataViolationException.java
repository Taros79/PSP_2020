package notas.ServidorModule.dao.errores;


public class DataViolationException extends Exception {


    public DataViolationException(String error) {
        super(error);
    }
}

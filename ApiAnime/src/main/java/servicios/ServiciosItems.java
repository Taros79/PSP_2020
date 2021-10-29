package servicios;

import dao.modelo.ModObjetos.Objeto;
import io.vavr.control.Either;

import java.util.List;

public interface ServiciosItems {
    Either<String, List<Objeto>> getAllItems();

    Objeto getItemsByNombre(String id);
}

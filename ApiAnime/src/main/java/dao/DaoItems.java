package dao;

import dao.modelo.ModObjetos.Objeto;
import io.vavr.control.Either;

import java.util.List;

public interface DaoItems {

    Either<String, List<Objeto>> getAllItems();

    Either<String, Objeto> getItemsByNombre(String id);
}

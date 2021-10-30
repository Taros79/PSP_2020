package dao;

import dao.modeloPropio.ObjetoPrp;
import io.vavr.control.Either;

import java.util.List;

public interface DaoItems {

    Either<String, List<ObjetoPrp>> getAllItems();

    Either<String, ObjetoPrp> getItemsByNombre(String id);
}

package servicios;

import dao.DaoItems;
import dao.modelo.ModObjetos.Objeto;
import io.vavr.control.Either;

import javax.inject.Inject;
import java.util.List;

public class ServiciosItems {
    DaoItems daoItems;

    @Inject
    public ServiciosItems (DaoItems items) {
        this.daoItems = items;
    }

    public Either<String, List<Objeto>> getAllItems() {
        return daoItems.getAllItems();
    }

    public Objeto getItemsByNombre(String id) {
        return daoItems.getItemsByNombre(id);
    }
}

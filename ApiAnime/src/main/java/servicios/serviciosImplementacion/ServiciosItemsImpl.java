package servicios.serviciosImplementacion;

import dao.DaoItems;
import dao.modelo.ModObjetos.Objeto;
import io.vavr.control.Either;
import servicios.ServiciosItems;

import javax.inject.Inject;
import java.util.List;

public class ServiciosItemsImpl implements ServiciosItems {
    public DaoItems daoItems;

    @Inject
    public ServiciosItemsImpl(DaoItems items) {
        this.daoItems = items;
    }

    @Override
    public Either<String, List<Objeto>> getAllItems() {
        return daoItems.getAllItems();
    }

    @Override
    public Objeto getItemsByNombre(String id) {
        return daoItems.getItemsByNombre(id);
    }
}

package servicios;

import dao.DaoItems;
import dao.modelo.ModObjetos.Objeto;

import java.util.List;

public class ServiciosItems {
    DaoItems daoItems = new DaoItems();

    public List<Objeto> getAllItems() {
        return daoItems.getAllItems();
    }

    public Objeto getItemsByNombre(String id) {
        return daoItems.getItemsByNombre(id);
    }
}

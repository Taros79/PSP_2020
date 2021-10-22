package pedidos.dao;

import pedidos.dao.modelo.Producto;

import java.util.ArrayList;
import java.util.List;

public class DaoProductos {
    private static List<Producto> productos = new ArrayList<>();

    public List<Producto> getProductos() {
        return productos;
    }

    public boolean addProducto(Producto producto){
            return productos.add(producto);
    }
}

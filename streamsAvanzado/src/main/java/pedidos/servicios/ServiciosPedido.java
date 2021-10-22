package pedidos.servicios;

import com.github.javafaker.Faker;
import pedidos.dao.DaoClientes;
import pedidos.dao.DaoPedidos;
import pedidos.dao.DaoProductos;
import pedidos.dao.modelo.*;

import java.util.List;

public class ServiciosPedido {

  public boolean addCliente(Cliente cliente) {
    DaoClientes daoClientes = new DaoClientes();
    return daoClientes.addCliente(cliente);
  }

  public Cliente deleteCliente(String email) {
    DaoClientes daoClientes = new DaoClientes();
    return daoClientes.deleteCliente(email);
  }

  public List<Cliente> getTodosClientes() {
    DaoClientes daoClientes = new DaoClientes();
    return daoClientes.getTodosClientes();
  }

  public Cliente getClientePorEmail(String email) {
    DaoClientes daoClientes = new DaoClientes();
    return daoClientes.getClientePorEmail(email);
  }

  public Cuenta addCuentaACliente(String email, Faker f) {
    DaoClientes daoClientes = new DaoClientes();
    Cuenta cuenta = null;

    Cliente cliente = getClientePorEmail(email);
    if (cliente != null) {
      String tarjeta;
      do {
        tarjeta = f.idNumber().valid();
      } while (!comprobarTC(tarjeta));
      cuenta = new Cuenta(tarjeta);
      daoClientes.addCuenta(cuenta, cliente);
    }
    return cuenta;
  }

  public List<Cuenta> cuentasCliente(Cliente cliente) {
    DaoClientes daoClientes = new DaoClientes();
    if (cliente != null) {
      return daoClientes.cuentasCliente(cliente);
    }
    return null;
  }

  public boolean comprobarTC(String tarjeta) {
    DaoClientes daoClientes = new DaoClientes();
    boolean tarjetaNueva = true;
    List<Cliente> clientes = daoClientes.getTodosClientes();
    for (int i = 0; i < clientes.size() && tarjetaNueva; i++) {
      for (Cuenta cuenta : daoClientes.cuentasCliente(clientes.get(i))) {
        if (cuenta.getNumeroTarjeta().equals(tarjeta)) {
          tarjetaNueva = false;
        }
      }
    }
    return tarjetaNueva;
  }

  public boolean addProducto(Producto producto) {
    DaoProductos daoProductos = new DaoProductos();
    boolean addedProduct = false;
    if (!daoProductos.getProductos().contains(producto)) {
      addedProduct = daoProductos.addProducto(producto);
    }
    return addedProduct;
  }

  public List<Producto> todosProductos() {
    DaoProductos daoProductos = new DaoProductos();
    return daoProductos.getProductos();
  }

  public boolean addLineaPedidoAPedidoSimple(Producto producto, int cantidad, PedidoSimple pedidoSimple) {
    DaoPedidos daoPedidos = new DaoPedidos();
    LineaPedido lineaPedido = new LineaPedido(producto, cantidad);
    boolean addedLinea = false;
    int cantidadTotal = cantidad;
    for (LineaPedido lp : pedidoSimple.getLineasPedido()) {
      cantidadTotal += lp.getCantidad();
    }
    if (cantidadTotal <= 20 && cantidad <= producto.getStock()) {
      daoPedidos.addLineaPedido(lineaPedido, pedidoSimple);
      producto.setStock(producto.getStock() - cantidad);
      addedLinea = true;
    }
    return addedLinea;
  }

  public void addPedidoSimpleAPedido(PedidoSimple pedidoSimple, PedidoCompuesto pedidoCompuesto) {
    DaoPedidos daoPedidos = new DaoPedidos();
    daoPedidos.addPedidoSimple(pedidoCompuesto, pedidoSimple);
  }

  public void addPedidoAPedidos(PedidoCompuesto pedidoCompuesto) {
    DaoPedidos daoPedidos = new DaoPedidos();
    daoPedidos.addPedidoCompuesto(pedidoCompuesto);
  }

  public void setTotalPrecio(PedidoCompuesto pedido) {
    for (PedidoSimple ps : pedido.getPedidosSimples()) {
      for (LineaPedido lp : ps.getLineasPedido()) {
        ps.setTotal(ps.getTotal() + lp.getPrecioTotal());
      }
      pedido.setTotalFactura(pedido.getTotalFactura() + ps.getTotal());
    }
  }

  public List<PedidoCompuesto> getTodosPedidos() {
    DaoPedidos daoPedidos = new DaoPedidos();
    return daoPedidos.getTodosPedidos();
  }

  public List<PedidoCompuesto> getPedidosPorCliente(String email) {
    DaoClientes daoClientes = new DaoClientes();
    DaoPedidos daoPedidos = new DaoPedidos();
    Cliente cliente = daoClientes.getClientePorEmail(email);
    return daoPedidos.getPedidosPorCliente(cliente);
  }

  public List<PedidoCompuesto> getPedidosPorEstado(Estado estado) {
    DaoPedidos daoPedidos = new DaoPedidos();
    return daoPedidos.getPedidoPorEstado(estado);
  }

  public boolean cobrarPedidos() {
    boolean todosCobrados = true;
    List<PedidoCompuesto> pedidosPendCobro = getPedidosPorEstado(Estado.PENDIENTECOBRO);
    for (PedidoCompuesto pc : pedidosPendCobro) {
      Cliente cliente = pc.getCliente();
      List<PedidoSimple> pedidosSimples = pc.getPedidosSimples();
      for (PedidoSimple ps : pedidosSimples) {
        if (!comprobarCuentas(pedidosPendCobro, pedidosSimples) && ps.getCuenta().getSaldo() < ps.getTotal()) {
          for (LineaPedido lineaPedido : ps.getLineasPedido()) {
            lineaPedido.getProducto().setStock(lineaPedido.getProducto().getStock() + lineaPedido.getCantidad());
          }
          pc.setEstado(Estado.RECHAZADO);
          todosCobrados = false;
        } else {
          pc.setEstado(Estado.COBRADO);
          ps.getCuenta().setSaldo(ps.getCuenta().getSaldo() - pc.getTotalFactura());
        }
      }
    }
    return todosCobrados;
  }

  public boolean comprobarCuentas(List<PedidoCompuesto> listaPedidosPendCobro, List<PedidoSimple> pedidosSimples) {
    boolean cuentas = true;
    for (PedidoCompuesto pc : listaPedidosPendCobro) {
      Cliente cliente = pc.getCliente();
      for (int i = 0; i < pedidosSimples.size() && cuentas; i++) {
        cuentas = cliente.getCuentas().contains(pedidosSimples.get(i).getCuenta());
      }
    }
    return cuentas;
  }

  public boolean enviarPedidos() {
    List<PedidoCompuesto> pedidos = getPedidosPorEstado(Estado.COBRADO);
    boolean enviados = true;
    for (PedidoCompuesto pedidoCompuesto : pedidos) {
      pedidoCompuesto.setEstado(Estado.REPARTO);
      if (!pedidoCompuesto.getEstado().equals(Estado.REPARTO)) {
        enviados = false;
      }
    }
    return enviados;
  }

  public boolean entregarPedidos() {
    List<PedidoCompuesto> pedidos = getPedidosPorEstado(Estado.REPARTO);
    boolean entregados = true;
    for (PedidoCompuesto pedidoCompuesto : pedidos) {
      pedidoCompuesto.setEstado(Estado.ENTREGADO);
      if (!pedidoCompuesto.getEstado().equals(Estado.ENTREGADO)) {
        entregados = false;
      }
    }
    return entregados;
  }

  public boolean dineroCuenta(Cliente cliente) {
    boolean dinero = false;
    for (Cuenta cuenta : cuentasCliente(cliente)) {
      if (cuenta.getSaldo() > 0) {
        dinero = true;
      }
    }
    return dinero;
  }
}

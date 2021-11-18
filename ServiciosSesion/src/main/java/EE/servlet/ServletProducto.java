package EE.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.ServiciosProductos;

import java.io.IOException;

@WebServlet(name = "ServletProducto", urlPatterns = {"/productos"})
public class ServletProducto extends HttpServlet {


    private ServiciosProductos sp;


    @Inject
    public ServletProducto(ServiciosProductos sp) {
        this.sp = sp;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var usuario = (String) request.getSession().getAttribute("usuario");


//        jsonb.fromJson(request.getReader(),ServiciosProductos.class);
//        Type listOfMyClassObject = new TypeToken<ArrayList<MyClass>>() {}.getType();

        sp.test();

        if (usuario != null) {
            // ha hecho login

        } else
            response.getWriter().println(sp.test());
    }
}

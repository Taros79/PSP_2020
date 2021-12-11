package servlet;

import dao.modelo.Usuario;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.ServiciosUsuarios;

import java.io.IOException;

@WebServlet(name = "ServletRegister",urlPatterns = {"/toRegister"})
public class ServletRegistro extends HttpServlet {

    private ServiciosUsuarios su;

    @Inject
    public ServletRegistro(ServiciosUsuarios su) {
        this.su = su;
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name =request.getParameter("userName");
        String pass = request.getParameter("password");
        Usuario u = new Usuario(name,pass);

        if (su.addUser(u) !=null)
        {
            request.getSession().setAttribute("user",u);
            request.getRequestDispatcher("/welcome.jsp").forward(request,response);
        }
        else
        {
            request.getRequestDispatcher("/loginIncorrecto.jsp").forward(request,response);
        }
    }

}

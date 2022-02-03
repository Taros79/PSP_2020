package quevedo.ServerBasket.EE.servelts.identificacion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import quevedo.ServerBasket.utils.constantes.Servlets;

import java.io.IOException;

@WebServlet(name = Servlets.SERVLET_CAMBIO_PASSW, value = Servlets.CAMBIO_PASSW_PATH)
public class ServletCambioPassw extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        cambioPassw(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cambioPassw(request, response);
    }

    private void cambioPassw(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String codAleatorio = request.getParameter(Servlets.CODIGO);

        request.setAttribute(Servlets.CODE, codAleatorio);

        request.getRequestDispatcher(Servlets.CAMBIO_PASS_JSP_PATH).forward(request, response);


    }


}

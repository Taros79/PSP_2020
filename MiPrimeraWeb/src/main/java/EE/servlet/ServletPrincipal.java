package EE.servlet;

import EE.constantes;
import com.github.javafaker.Faker;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Servlet", value = constantes.SERVLET_PINTURA)
public class ServletPrincipal extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Faker f = new Faker();
        response.setContentType(constantes.TEXT_HTML);
        PrintWriter pw = response.getWriter();
        String color = request.getParameter(constantes.COLOR_ELEGIDO);
        String c = "<body BGCOLOR=" + f.color().name() + ">";
        if (color.equals("Rojo"))
            pw.println("<body BGCOLOR=#ff000>");
        if (color.equals("Verde"))
            pw.println("<body BGCOLOR=green>");
        if (color.equals("Azul"))
            pw.println("<body BGCOLOR=blue>");
        if (color.equals("Amarillo"))
            pw.println("<body BGCOLOR=yellow>");
        if (color.equals("Negro"))
            pw.println("<body BGCOLOR=black>");
        if (color.equals("Random"))
            pw.println(c);
        pw.println("<center><h2>El color seleccionado fue: " + color + "</h2></center>");
        pw.close();
    }

}

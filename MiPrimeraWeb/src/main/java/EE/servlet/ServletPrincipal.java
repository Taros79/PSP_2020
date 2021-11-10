package EE.servlet;

import com.github.javafaker.Faker;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Servlet", value = "/ServletPintura")
public class ServletPrincipal extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Faker f = new Faker();
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        String color = request.getParameter("colorElegido");
        String c = "<body BGCOLOR=" + f.color().name() + ">";
        if (color.equals("Rojo"))
            pw.println("<body BGCOLOR=#ff000>");
        if (color.equals("Verde"))
            pw.println("<body BGCOLOR=green>");
        if (color.equals("Azul"))
            pw.println("<body BGCOLOR=blue>");
        if (color.equals("Amarillo"))
            pw.println("<body BGCOLOR=yellow>");
        if (color.equals("Negro")) {
            pw.println("<body BGCOLOR=black>");
        }
        if (color.equals("Aleatorio")) {
            pw.println(c);
        }
        pw.println("<center><h2>El color seleccionado fue: " + color + "</h2></center>");
        ;
        pw.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

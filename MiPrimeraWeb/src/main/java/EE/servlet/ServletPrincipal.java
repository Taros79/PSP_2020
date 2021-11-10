package EE.servlet;

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
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        String color = request.getParameter("colorElegido");

        if (color.equals("red"))
            pw.println("<body BGCOLOR=red>");
        if (color.equals("green"))
            pw.println("<body BGCOLOR=green>");
        if (color.equals("blue"))
            pw.println("<body BGCOLOR=blue>");
        if (color.equals("yellow"))
            pw.println("<body BGCOLOR=yellow>");
        if (color.equals("black"))
            pw.println("<body BGCOLOR=black>");
        pw.println("<center><h2>The selected color is:" + color + "</h2></center>");
        ;
        pw.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

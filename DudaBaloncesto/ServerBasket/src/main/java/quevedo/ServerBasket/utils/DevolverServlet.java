package quevedo.ServerBasket.utils;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import quevedo.ServerBasket.utils.constantes.ConstantesParametros;
import quevedo.common.error.ApiError;
import quevedo.common.modelo.UserLoged;

import java.io.IOException;
import java.io.PrintWriter;

public class DevolverServlet {


    public static void devolverError(HttpServletResponse response, int codigo, String mensaje) throws IOException {
        String apiErrorJson = new Gson().toJson(new ApiError(mensaje));
        PrintWriter out = response.getWriter();
        response.setContentType(ConstantesParametros.APPLICATION_JSON);
        response.setStatus(codigo);
        response.setCharacterEncoding(ConstantesParametros.UTF_8);
        out.print(apiErrorJson);
        out.flush();
    }

    public static void devolverUserLoged(HttpServletResponse response, int codigo, UserLoged user) throws IOException {
        String apiErrorJson = new Gson().toJson(user);
        PrintWriter out = response.getWriter();
        response.setContentType(ConstantesParametros.APPLICATION_JSON);
        response.setStatus(codigo);
        response.setCharacterEncoding(ConstantesParametros.UTF_8);
        out.print(apiErrorJson);
        out.flush();


    }


}

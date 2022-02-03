package quevedo.ServerBasket.EE.servelts.identificacion;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import quevedo.ServerBasket.services.PasswordHash;
import quevedo.ServerBasket.services.identificacion.CambioPasswService;
import quevedo.ServerBasket.utils.constantes.Mensajes;
import quevedo.ServerBasket.utils.constantes.Servlets;
import quevedo.common.utils.StringsCommon;

import java.io.IOException;

@WebServlet(name = Servlets.SERVLET_CAMBIA_PASSW, value = Servlets.ACTUALIZAR_PASSW_PATH)
public class ServletActualizaPassw extends HttpServlet {

    private final CambioPasswService cambioPasswService;
    private final PasswordHash passwordHash;

    @Inject
    public ServletActualizaPassw(CambioPasswService cambioPasswService, PasswordHash passwordHash) {
        this.cambioPasswService = cambioPasswService;
        this.passwordHash = passwordHash;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        actualizar(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        actualizar(request, response);
    }

    private void actualizar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String newPassw = passwordHash.hash(request.getParameter(StringsCommon.PASSWD));
        String codPass = request.getParameter(Servlets.CODE);

        Either<String, Boolean> resultCompruebaTiempo = cambioPasswService.confirmaTiempo(codPass);
        Either<String, Boolean> modificaPass;
        if (resultCompruebaTiempo.isRight()) {

//            Cambio contra
            modificaPass = cambioPasswService.modificaPass(newPassw, codPass);
            if (modificaPass.isRight()) {
//
                request.setAttribute(Servlets.MENSAJE, Mensajes.CONTRASEÑA_ACTUALIZADA);
                request.getRequestDispatcher(Servlets.MENSAJE_ACTUALIZACION_PATH).forward(request, response);

            } else {
//                Tratar errores al modificar la passw
                request.setAttribute(Servlets.MENSAJE, modificaPass.getLeft());
                request.getRequestDispatcher(Servlets.MENSAJE_ACTUALIZACION_PATH).forward(request, response);
            }

        } else {
//            Se pasa de tiempo

            request.setAttribute(Servlets.MENSAJE, Mensajes.VUELVA_A_ENVIAR_OTRA_SOLICITUD_DE_CAMBIO_DE_CONTRASEÑA);
            request.getRequestDispatcher(Servlets.MENSAJE_ACTUALIZACION_PATH).forward(request, response);


        }


    }


}

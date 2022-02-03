package quevedo.ServerBasket.EE.servelts.identificacion;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import quevedo.ServerBasket.services.identificacion.MandarMail;
import quevedo.ServerBasket.services.identificacion.RegisterService;
import quevedo.ServerBasket.utils.CodeGenartor;
import quevedo.ServerBasket.utils.constantes.ConstantesParametros;
import quevedo.ServerBasket.utils.constantes.CorreosStrings;
import quevedo.ServerBasket.utils.constantes.Mensajes;
import quevedo.ServerBasket.utils.constantes.Servlets;

import java.io.IOException;

@WebServlet(name = Servlets.SERVLET_ACTIVACION, value = Servlets.ACTIVAR_PATH)
@Log4j2
public class ServletActivacion extends HttpServlet {

    private final RegisterService registerService;
    private final MandarMail mandarMail;

    @Inject
    public ServletActivacion(RegisterService registerService, MandarMail mandarMail) {
        this.registerService = registerService;
        this.mandarMail = mandarMail;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        activar(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        activar(request, response);
    }


    private void activar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String codActivacion = request.getParameter(Servlets.CODIGO);

        Either<String, Boolean> resultadoFecha = registerService.comprobarFecha(codActivacion);
        Either<String, Boolean> resultadoActivacion;
        Either<String, Boolean> resultadoAumentoTiempo;


        if (resultadoFecha.isRight()) {
            if (resultadoFecha.get()) {

                resultadoActivacion = registerService.activar(codActivacion);
//                Trato Either de la activacion
                if (resultadoActivacion.isLeft()) {
                    request.getSession().setAttribute(Servlets.MENSAJE_ERROR, resultadoActivacion.getLeft());
                    request.getRequestDispatcher(Servlets.JSP_ERRORES_PATH).forward(request, response);
                } else {
                    request.getSession().setAttribute(Servlets.MENSAJE, Mensajes.SU_CUENTA_HA_SIDO_ACTIVADA);
                    request.getRequestDispatcher(Servlets.JSP_ACTIVACION_PATH).forward(request, response);
                }

            } else {
//                Mando mail y aumento el tiempo de activacion en 15 mins
                String randomCode = CodeGenartor.randomBytes();
                resultadoAumentoTiempo = registerService.cambiarCodActivacion(randomCode, codActivacion);
                if (resultadoAumentoTiempo.isRight()) {
                    try {
                        String correo = request.getParameter(ConstantesParametros.CORREO);
                        String mensajeCorreo = CorreosStrings.REACTIVAR_CUENTA_HTML + randomCode + CorreosStrings.CORREO_HTML+correo +CorreosStrings.REACTIVAR_ENLACE;
                        mandarMail.generateAndSendEmail(correo,
                                mensajeCorreo
                                , CorreosStrings.MAIL_DE_REACTIVACION);
                        request.getRequestDispatcher(Servlets.WEB_INF_ENVIADO_HTML).forward(request, response);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }

                } else {
//                    Trato Error cambio Codigo
                    request.getSession().setAttribute(Servlets.MENSAJE_ERROR, resultadoAumentoTiempo.getLeft());
                    request.getRequestDispatcher(Servlets.JSP_ERRORES_PATH).forward(request, response);
                }

            }

        } else {
//            Trato Errores del Either de la fecha
            request.getSession().setAttribute(Servlets.MENSAJE_ERROR, resultadoFecha.getLeft());
            request.getRequestDispatcher(Servlets.JSP_ERRORES_PATH).forward(request, response);

        }


    }

}

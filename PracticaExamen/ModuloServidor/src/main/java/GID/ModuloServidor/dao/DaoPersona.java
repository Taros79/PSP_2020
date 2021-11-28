package GID.ModuloServidor.dao;

import GID.Commons.EE.utils.ApiRespuesta;
import GID.Commons.dao.modelo.Persona;
import GID.ModuloServidor.EE.errores.ApiError;
import GID.ModuloServidor.EE.rest.Constantes;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
public class DaoPersona {

    private static List<Persona> personas = new ArrayList<>();

    static {
        personas.add(new Persona("1", "Carlos", "Casad@", "H",
                "Madrid", "2", LocalDateTime.now(), new ArrayList<>()));
        personas.add(new Persona("2", "Stefan", "Casad@", "M",
                "Madrid", "1", LocalDateTime.now(), new ArrayList<>()));
        personas.add(new Persona("3", "looo", "Solter@", "H",
                "Madrid", "", LocalDateTime.now(), new ArrayList<>()));
        personas.add(new Persona("4", "lllllllll", "Solter@", "M",
                "aqui", "", LocalDateTime.now(), new ArrayList<>()));
        personas.add(new Persona("5", "Mauricio", "Solter@", "H",
                "alla", "", LocalDateTime.now(), new ArrayList<>()));
        personas.add(new Persona("6", "Gem", "Casad@", "M",
                "paca", "7", LocalDateTime.now(), new ArrayList<>()));
        personas.add(new Persona("7", "Novillin", "Casad@", "H",
                "", "6", LocalDateTime.now(), new ArrayList<>()));
    }

    public DaoPersona() {

    }

    public Either<ApiError, Persona> getPersona(String id) {
        Either<ApiError, Persona> resultado;
        Persona p = personas.stream().filter(persona -> Objects.equals(persona.getId(), id))
                .findFirst().orElse(null);
        try {
            if (p != null) {
                resultado = Either.right(p);
            } else {
                resultado = Either.left(new ApiError(Constantes.NO_SE_ENCONTRO_EL_OBJETO, LocalDateTime.now()));
            }
        } catch (Exception e) {
            resultado = Either.left(new ApiError(Constantes.ERROR_CON_EL_OBJETO, LocalDateTime.now()));
            log.error(e.getMessage(), e);
        }
        return resultado;
    }

    public Either<ApiError, List<Persona>> getAll() {
        Either<ApiError, List<Persona>> resultado;
        try {
            if (personas.size() == 0) {
                resultado = Either.left(new ApiError(Constantes.LISTA_DE_OBJETOS_VACIA, LocalDateTime.now()));
            } else {
                resultado = Either.right(personas);
            }
        } catch (Exception e) {
            resultado = Either.left(new ApiError(Constantes.ERROR_CON_EL_OBJETO, LocalDateTime.now()));
            log.error(e.getMessage(), e);
        }
        return resultado;
    }

    public Either<ApiError, Persona> addPersona(Persona p) {
        Either<ApiError, Persona> resultado;
        int id = Integer.parseInt(personas.get(personas.size() - 1).getId()) + 1;
        p.setId(String.valueOf(id));
        try {
            personas.add(p);
            resultado = Either.right(p);
        } catch (Exception e) {
            resultado = Either.left(new ApiError(Constantes.ERROR_CON_EL_OBJETO, LocalDateTime.now()));
            log.error(e.getMessage(), e);
        }
        return resultado;
    }

    public Either<ApiError, ApiRespuesta> borrarPersona(String id) {
            Either<ApiError, ApiRespuesta> resultado;
            boolean borrado = false;
            int numeroAbandonos = 0;

            Persona p = personas.stream()
                    .filter(persona -> persona.getId().equals(id)).findFirst().orElse(null);

            if (p != null) {
                String idMujer = p.getIdPersonaCasada();
                List<Persona> hijos = p.getHijos();

                for (int i = 0; i < hijos.size(); i++) {
                    String idHijo = hijos.get(i).getId();
                    personas.remove(personas.stream()
                            .filter(persona -> persona.getId().equals(idHijo))
                            .findFirst().orElse(null));
                }

                personas.remove(personas.stream()
                        .filter(persona -> persona.getId().equals(idMujer))
                        .findFirst().orElse(null));

                personas.remove(personas.stream()
                        .filter(persona -> persona.getId().equals(id))
                        .findFirst().orElse(null));


                if (!Objects.equals(p.getIdPersonaCasada(), "")) {
                    numeroAbandonos = 2;
                } else {
                    numeroAbandonos = 1;
                }

                numeroAbandonos = numeroAbandonos + p.getHijos().size();

                borrado = true;
            }

            if (borrado) {
                resultado = Either.right(new ApiRespuesta
                        ("Fueron exiliadas " + numeroAbandonos + " personas en total.", LocalDateTime.now()));
            } else {
                resultado = Either.left(new ApiError("No se pudo borrar", LocalDateTime.now()));
            }
            return resultado;
        }

    public Either<ApiError, ApiRespuesta> casamientoPareja (String idH, String idM) {
        Either<ApiError, ApiRespuesta> resultado;

        if(!idH.isEmpty() && !idM.isEmpty()){
            int posicion = 0;
            for (int i = 0; i < personas.size(); i++) {
                if (idH.equals(personas.get(i).getId())) {
                    posicion = i;
                }
            }
            personas.get(posicion).setIdPersonaCasada(idM);
            personas.get(posicion).setEstadoCivil("Casad@");

            posicion = 0;
            for (int i = 0; i < personas.size(); i++) {
                if (idM.equals(personas.get(i).getId())) {
                    posicion = i;
                }
            }
            personas.get(posicion).setIdPersonaCasada(idH);
            personas.get(posicion).setEstadoCivil("Casad@");

            resultado = Either.right(new ApiRespuesta
                    ("Personas casadas en santo matrimonio... amen", LocalDateTime.now()));
        }else{
            resultado = Either.left(new ApiError("Alguien interrumpió el casamiento", LocalDateTime.now()));
        }
        return resultado;
    }

}

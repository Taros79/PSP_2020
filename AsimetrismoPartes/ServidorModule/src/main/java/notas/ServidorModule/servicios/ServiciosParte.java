package notas.ServidorModule.servicios;

import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import notas.CommonModule.modelo.PartesCompartidos;
import notas.CommonModule.modelo.Usuario;
import notas.CommonModule.modeloDTO.ParteDesencriptadoDTO;
import notas.CommonModule.modeloDTO.ParteProfesorPadre;
import notas.ServidorModule.EE.security.encriptaciones.Constantes;
import notas.ServidorModule.EE.security.encriptaciones.Encriptar;
import notas.ServidorModule.dao.ConstantesSQL;
import notas.ServidorModule.dao.DaoAlumno;
import notas.ServidorModule.dao.DaoParte;
import notas.ServidorModule.dao.DaoUsuario;
import notas.ServidorModule.dao.errores.OtraException;

import java.security.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Log4j2
public class ServiciosParte {

    private final DaoParte daoParte;
    private final DaoUsuario daoUsuario;
    private final DaoAlumno daoAlumno;
    private final Encriptar encriptar;

    @Inject
    public ServiciosParte(DaoParte daoParte, DaoUsuario daoUsuario, DaoAlumno daoAlumno, Encriptar encriptar) {
        this.daoParte = daoParte;
        this.daoUsuario = daoUsuario;
        this.daoAlumno = daoAlumno;
        this.encriptar = encriptar;
    }

    public List<ParteDesencriptadoDTO> getPartesByUser(int idUsuario) {
        List<ParteDesencriptadoDTO> partesDesencriptados = new ArrayList<>();
        List<PartesCompartidos> pc = daoParte.getPartesByUser(idUsuario);
        Usuario usuario = daoUsuario.getUsuarioById(idUsuario);

        //Recorremos todos los partes compartidos
        for (PartesCompartidos partesCompartidos : pc) {
            //Con la idParte de partes compartidos obtenemos el parte
            var parte = daoParte.getParteById(partesCompartidos.getIdParte());

            //Si el usuario es padre solo vera los aceptados, el profesor los suyos y jefatura todos
            if (usuario.getIdTipoUsuario() == ConstantesSQL.TIPO_JEFATURA ||
                    parte.getIdTipoEstado() == ConstantesSQL.ESTADO_CONFIRMADO &&
                            usuario.getIdTipoUsuario() != ConstantesSQL.TIPO_JEFATURA ||
                    usuario.getIdTipoUsuario() == ConstantesSQL.TIPO_PROFESOR) {
                var randomDesencriptada =
                        encriptar.desencriptarRSAClaveCifrada(partesCompartidos.getClaveCifrada(), usuario);
                if (randomDesencriptada.isRight()) {
                    var mensajeParte = encriptar.desencriptarAESTextoConRandom(parte.getDescripcion(), randomDesencriptada.get());
                    if (mensajeParte.isRight()) {
                        var alumno = daoAlumno.getAlumnoById(parte.getIdAlumno());
                        //mirar
                        if (Objects.nonNull(alumno)) {
                            try {
                                //ver firma
                                String firmaAComprobar;
                                Usuario userPublic;
                                if (usuario.getIdTipoUsuario() == ConstantesSQL.TIPO_PADRE) {
                                    userPublic = daoUsuario.getUsuarioById(ConstantesSQL.ID_JEFATURA);
                                } else {
                                    userPublic = daoUsuario.getUsuarioById(parte.getIdProfesor());
                                }

                                PublicKey publicKeyUsuarioQueFirmo = encriptar.getPublicKey(userPublic.getNombre()).get();
                                //Y ya con la publica, comprobamos:
                                Signature sign;
                                sign = Signature.getInstance(Constantes.SHA_256_WITH_RSA);
                                MessageDigest hash = MessageDigest.getInstance(Constantes.SHA_512);
                                //Lo ponemos en verificar
                                sign.initVerify(publicKeyUsuarioQueFirmo);
                                //Aqui va la pass ya encriptada
                                sign.update(hash.digest(mensajeParte.get().getBytes()));
                                if (usuario.getIdTipoUsuario() != ConstantesSQL.TIPO_PADRE) {
                                    firmaAComprobar = parte.getFirmaProfesor();
                                } else {
                                    firmaAComprobar = parte.getFirmaJefatura();
                                }
                                byte[] firma = Base64.getUrlDecoder().decode(firmaAComprobar);

                                if (sign.verify(firma)) {
                                    //Y aqui implica que es correcto que lo firmo él
                                    partesDesencriptados.add(new ParteDesencriptadoDTO(parte.getId(), mensajeParte.get(),
                                            alumno.getNombre(), parte.getIdTipoEstado()));
                                }
                            } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
                                e.printStackTrace();
                            }
                        } else {
                            log.error("No se ha encontrado el alumno con id: " + parte.getIdAlumno());
                            throw new OtraException(ConstantesSQL.ALUMNO_NO_ENCONTRADO);
                        }
                    } else {
                        log.error("Error al desencriptar el mensaje de la parte con id: " + partesCompartidos.getIdParte());
                        throw new OtraException(ConstantesSQL.ERROR_AL_DESENCRIPTAR_MENSAJE);
                    }

                } else {
                    log.error(randomDesencriptada.getLeft());
                    throw new OtraException(ConstantesSQL.ERROR_AL_DESENCRIPTAR_CLAVECIFRADA);
                }
            }
        }
        return partesDesencriptados;
    }

    public String addParte(ParteProfesorPadre parte) {
        return daoParte.addParte(parte);
    }

    public String addParteCompartido(int idUsuario, int idParte) {
        return daoParte.addParteCompartido(idUsuario, idParte);
    }

    public String updateParte(int idParte, int estado) {
        var datos = daoParte.updateParte(idParte, estado);
        return addParteCompartidoPadres(datos.getUsuario().getId(), idParte, datos.getRandomDesencriptada());
    }

    public String addParteCompartidoPadres(int idUsuario, int idParte, String random) {
        var usuario = daoUsuario.getUsuarioById(idUsuario);
        String resultado;
        if (usuario != null) {
            var claveCifrada = encriptar.encriptarRSA_Padres(usuario.getNombre(), random);
            if (claveCifrada.isRight()) {
                resultado = daoParte.addParteCompartidoPadres(idUsuario, idParte, claveCifrada.get());
            } else {
                log.error(claveCifrada.getLeft());
                throw new OtraException(ConstantesSQL.ERROR_DEL_SERVIDOR);
            }
        } else {
            log.error("No se ha encontrado el usuario con id: " + idUsuario);
            throw new OtraException(ConstantesSQL.USUARIO_NO_ENCONTRADO);
        }
        return resultado;
    }

    public String firmarPartePadre(int idUsuario, int idParte, String mensaje) {
        return daoParte.firmarPartePadre(idUsuario, idParte, mensaje);
    }
}

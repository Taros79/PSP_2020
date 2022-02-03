package quevedo.ServerBasket.services.equipos;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import quevedo.ServerBasket.dao.EquiposDao;
import quevedo.common.modelo.ResultadosPartidos;
import quevedo.common.modelo.dto.EquipoDTO;

import java.util.List;

public class EquiposService {


    private final EquiposDao equiposDao;

    @Inject
    public EquiposService(EquiposDao equiposDao) {
        this.equiposDao = equiposDao;
    }

    public Either<String, EquipoDTO> addEquipo(EquipoDTO equipoDTO) {


        return equiposDao.addEquipo(equipoDTO);

    }

    public Either<String, List<ResultadosPartidos>> getPartidosPorEquipo(String nombre) {
        return equiposDao.getPartidosFiltradosPorEquipo(nombre);
    }


    public Either<String, List<EquipoDTO>> getAllEquipos() {
        return equiposDao.getAllEquipos();
    }


}

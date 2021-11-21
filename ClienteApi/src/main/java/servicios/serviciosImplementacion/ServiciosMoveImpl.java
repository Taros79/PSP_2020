package servicios.serviciosImplementacion;

import dao.DaoMove;
import dao.DaoPokemon;
import dao.modelo.Move;
import dao.modelo.Pokemon;
import io.vavr.control.Either;
import servicios.ServiciosMove;
import servicios.ServiciosPokemon;

import javax.inject.Inject;
import java.util.List;

public class ServiciosMoveImpl implements ServiciosMove {
    public DaoMove daoMove;

    @Inject
    public ServiciosMoveImpl(DaoMove daoMove) {
        this.daoMove = daoMove;
    }

    @Override
    public Either<String, List<Move>> getAllMove() {
        return daoMove.getAllMove();
    }

    @Override
    public Either<String, Move> getMove(String id) {
        return daoMove.getMove(id);
    }

    @Override
    public Either<String, Move> addMove(String id, String name, String descripcion) {
        return daoMove.addMove(id,name,descripcion);
    }

    @Override
    public Either<String, Move> deleteMove(String id) {
        return daoMove.deleteMove(id);
    }
}

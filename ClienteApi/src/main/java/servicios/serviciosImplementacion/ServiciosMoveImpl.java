package servicios.serviciosImplementacion;

import dao.DaoMove;
import dao.modelo.Move;
import io.vavr.control.Either;
import servicios.ServiciosMove;

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
    public Either<String, Move> addMove(Move m) {
        return daoMove.addMove(m);
    }

    @Override
    public Either<String, Move> deleteMove(String id) {
        return daoMove.deleteMove(id);
    }

    @Override
    public Either<String, Move> getDatosMovimiento(String id) {
        return daoMove.getDatosMovimiento(id);
    }

    @Override
    public Either<String, Move> actualizarMove(Move m) {
        return daoMove.actualizarMove(m);
    }
}

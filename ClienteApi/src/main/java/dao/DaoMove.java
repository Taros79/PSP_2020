package dao;

import dao.modelo.Move;
import io.vavr.control.Either;

import java.util.List;

public interface DaoMove {

    Either<String, List<Move>> getAllMove();

    Either<String, Move> getMove(String id);

    Either<String, Move> addMove(Move m);

    Either<String, Move> deleteMove(String id);

    Either<String, Move> getDatosMovimiento(String id);

    Either<String, Move> actualizarMove(Move m);
}

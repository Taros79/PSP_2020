package dao;

import dao.modelo.Move;
import dao.modelo.Pokemon;
import io.vavr.control.Either;

import java.util.List;

public interface DaoMove {

    Either<String, List<Move>> getAllMove();

    Either<String, Move> getMove(String id);

    Either<String, Move> addMove(String id, String name, String descripcion);

    Either<String, Move> deleteMove(String id);
}

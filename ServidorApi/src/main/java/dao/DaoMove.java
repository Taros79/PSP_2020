package dao;

import EE.errores.ApiError;
import EE.errores.CustomException;
import dao.modelo.Move;
import dao.modelo.Pokemon;
import io.vavr.control.Either;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DaoMove {

    private static List<Move> moves = new ArrayList<>();

    static {
        moves.add(new Move("1","Poner un ceraco", "Movimiento estrella de Oscar, " +
                "el cual, mediante un sutil movimiento de mano te hunde en la miseria mas absoluta. " +
                "Especialmente eficaz contra el alumnado."));
    }

    public DaoMove() {

    }

    public Either<ApiError, Move> getMove(String id)
    {
        Move m = moves.stream()
                .filter(move -> move.getId().equals(id))
                .findFirst().orElse(null);
        if (m!=null) {
            return Either.right(m);
        }
        else
        {
            return Either.left(new ApiError("error not found", LocalDateTime.now()));
        }
    }

    public List<Move> getAll()
    {
        if (moves.size()==0)
        {
            throw new CustomException("lista vacia", Response.Status.NOT_FOUND);
        }
        return moves;
    }

    public Move addMove(Move move) {
        int id = Integer.parseInt(moves.get(moves.size() - 1).getId()) + 1;
        move.setId(String.valueOf(id));
        moves.add(move);
        return move;
    }

    public boolean borrarMove(String id) {
        return moves.remove(moves.stream()
                .filter(move -> move.getId().equals(id))
                .findFirst().orElse(null));
    }
}

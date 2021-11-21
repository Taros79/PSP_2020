package dao.daoImplementacion;

import dao.DaoMove;
import dao.modelo.Move;
import dao.retrofit.PokemonApi;
import dao.utils.ConfigurationSingleton_OkHttpClient;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import retrofit2.Response;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

@Log4j2
public class DaoMoveImp implements DaoMove {

    public ConfigurationSingleton_OkHttpClient configurationSingleton_okHttpClient;

    @Inject
    public DaoMoveImp(ConfigurationSingleton_OkHttpClient configurationSingleton_okHttpClient) {
        this.configurationSingleton_okHttpClient = configurationSingleton_okHttpClient;
    }

    PokemonApi pokemonApi = configurationSingleton_okHttpClient.getInstance().create(PokemonApi.class);

    @Override
    public Either<String, List<Move>> getAllMove() {
        Either<String, List<Move>> resultado;

        try {
            Response<List<Move>> response = pokemonApi.getRecursosMove().execute();

            if (response.isSuccessful() && Objects.requireNonNull(response.body()) != null) {
                assert response.body() != null;
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left("Lista de movimientos no valida");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left("Error bbdd");
        }
        return resultado;
    }

    @Override
    public Either<String, Move> getMove(String id) {
        Either<String, Move> resultado;

        try {
            Response<Move> response = pokemonApi.getUnMovimiento(id).execute();

            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left("Movimiento no valido");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left("Error bbdd");
        }
        return resultado;
    }

    @Override
    public Either<String, Move> addMove(String id, String name, String descripcion) {
        Either<String, Move> resultado;

        try {
            Response<Move> response = pokemonApi.addMove(id, name, descripcion).execute();

            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left("Movimiento no valido");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left("Error bbdd");
        }
        return resultado;
    }

    @Override
    public Either<String, Move> deleteMove(String id) {
        Either<String, Move> resultado;

        try {
            Response<Move> response = pokemonApi.deleteMove(id).execute();

            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left("Movimiento no valido");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left("Error bbdd");
        }
        return resultado;
    }
}

package dao.daoImplementacion;

import dao.DaoMove;
import dao.modelo.Move;
import dao.retrofit.PokemonApi;
import dao.utils.ConfigurationSingleton_OkHttpClient;
import dao.utils.Constantes;
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
                resultado = Either.left(Constantes.OBJETO_NO_VALIDO);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.ERROR_EN_BBDD);
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
                resultado = Either.left(Constantes.OBJETO_NO_VALIDO);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.ERROR_EN_BBDD);
        }
        return resultado;
    }

    @Override
    public Either<String, Move> addMove(Move m) {
        Either<String, Move> resultado;
        try {
            Response<Move> response = pokemonApi.addMove(m).execute();

            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left(Constantes.OBJETO_NO_VALIDO);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.ERROR_EN_BBDD);
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
                resultado = Either.left(Constantes.OBJETO_NO_VALIDO);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.ERROR_EN_BBDD);
        }
        return resultado;
    }

    @Override
    public Either<String, Move> getDatosMovimiento(String id) {
        Either<String, Move> resultado;

        try {
            Response<Move> response = pokemonApi.getUnMovimiento(id).execute();
            if (response.isSuccessful()) {
                Objects.requireNonNull(response.body());
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left(Constantes.OBJETO_NO_VALIDO);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.ERROR_EN_BBDD);
        }
        return resultado;
    }

    @Override
    public Either<String, Move> actualizarMove(Move m) {
        Either<String, Move> resultado;
        try {
            Response<Move> response = pokemonApi.actualizarMove(m).execute();

            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left(Constantes.OBJETO_NO_VALIDO);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.ERROR_EN_BBDD);
        }
        return resultado;
    }
}

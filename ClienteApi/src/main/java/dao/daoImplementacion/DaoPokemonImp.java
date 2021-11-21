package dao.daoImplementacion;

import dao.DaoPokemon;
import dao.modelo.Move;
import dao.modelo.Pokemon;
import dao.retrofit.PokemonApi;
import dao.utils.ConfigurationSingleton_OkHttpClient;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import retrofit2.Response;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

@Log4j2
public class DaoPokemonImp implements DaoPokemon {

   public ConfigurationSingleton_OkHttpClient configurationSingleton_okHttpClient;

    @Inject
    public DaoPokemonImp(ConfigurationSingleton_OkHttpClient configurationSingleton_okHttpClient) {
        this.configurationSingleton_okHttpClient = configurationSingleton_okHttpClient;
    }

    PokemonApi pokemonApi = configurationSingleton_okHttpClient.getInstance().create(PokemonApi.class);

    @Override
    public Either<String, List<Pokemon>> getAllPokemon() {
        Either<String, List<Pokemon>> resultado;

        try {
            Response<List<Pokemon>> response = pokemonApi.getRecursosPokemon().execute();

            if (response.isSuccessful() && Objects.requireNonNull(response.body()) != null) {
                assert response.body() != null;
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left("Lista pokemones no valida");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left("Error bbdd");
        }
        return resultado;
    }

    @Override
    public Either<String, Pokemon> getDatosByNombre(String id) {
        Either<String, Pokemon> resultado;

        try {
            Response<Pokemon> response = pokemonApi.getRecursosUnPokemon(id).execute();

            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left("Pokemon no valido");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left("Error bbdd");
        }
        return resultado;
    }

    @Override
    public Either<String, Pokemon> addPokemon(Pokemon p) {
        Either<String, Pokemon> resultado;

        try {
            Response<Pokemon> response = pokemonApi.addPokemon(p).execute();

            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left("Pokemon no valido");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left("Error bbdd");
        }
        return resultado;
    }

    @Override
    public Either<String, Pokemon> deletePokemon(String id) {
        Either<String, Pokemon> resultado;

        try {
            Response<Pokemon> response = pokemonApi.deletePokemon(id).execute();

            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left("Pokemon no valido");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left("Error bbdd");
        }
        return resultado;
    }

    @Override
    public Either<String, List<Move>> getMovimientosPorId(String id) {
        Either<String, List<Move>> resultado;

        try {
            Response<Pokemon> response = pokemonApi.getRecursosUnPokemon(id).execute();

            if (response.isSuccessful() && Objects.requireNonNull(response.body()).getMoves() != null) {
                resultado = Either.right(response.body().getMoves());
            } else {
                resultado = Either.left("WHOs IS TATH POKEMONE!!!");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left("Error bbdd");
        }
        return resultado;
    }
}

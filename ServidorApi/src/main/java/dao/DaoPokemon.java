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

public class DaoPokemon {

    private static List<Pokemon> pokemones = new ArrayList<>();

    static {
        pokemones.add(new Pokemon("1","Oscar", ".png",LocalDateTime.now(), new ArrayList<>()));
        pokemones.add(new Pokemon("2","Stefan", ".png",LocalDateTime.now(), new ArrayList<>()));
        pokemones.add(new Pokemon("3","Novillo", ".png",LocalDateTime.now(), new ArrayList<>()));
        pokemones.add(new Pokemon("4","Carlos", ".png",LocalDateTime.now(), new ArrayList<>()));
    }

    public DaoPokemon() {

    }

    public Either<ApiError, Pokemon> getPokemon(String id)
    {
        Pokemon p = pokemones.stream()
                .filter(pokemon -> pokemon.getId().equals(id))
                .findFirst().orElse(null);
        if (p!=null) {
            return Either.right(p);
        }
        else
        {
            return Either.left(new ApiError("error not found", LocalDateTime.now()));
        }
    }

    public List<Pokemon> getAll()
    {
        if (pokemones.size()==0)
        {
            throw new CustomException("lista vacia", Response.Status.NOT_FOUND);
        }
        return pokemones;
    }

    public Pokemon addPokemon(Pokemon pokemon)
    {
        pokemon.setId("" + (pokemones.size()+1));
        pokemones.add(pokemon);
        return pokemon;
    }

    public boolean borrarPokemon(String id) {
        return pokemones.remove(pokemones.stream()
                .filter(pokemon -> pokemon.getId().equals(id))
                .findFirst().orElse(null));
    }
}

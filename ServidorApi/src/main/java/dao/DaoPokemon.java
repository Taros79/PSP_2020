package dao;

import EE.errores.ApiError;
import EE.errores.CustomException;
import dao.modelo.Move;
import dao.modelo.Pokemon;
import io.vavr.control.Either;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DaoPokemon {

    private static List<Pokemon> pokemones = new ArrayList<>();

    static {
        pokemones.add(new Pokemon("1","Oscar", "https://i.pinimg.com/236x/57/d9/d8/57d9d8b0315630360590a4dfd57ef17a--cartoon-people-cartoon-characters.jpg",LocalDateTime.now(),
                new ArrayList<>(List.of(new Move("1", "Poner un ceraco", "Movimiento estrella de Oscar, " +
                        "el cual, mediante un sutil movimiento de mano te hunde en la miseria mas absoluta. " +
                        "Especialmente eficaz contra el alumnado.")))));
        pokemones.add(new Pokemon("2","Stefan", "https://l450v.alamy.com/450ves/2by026c/graciosos-personajes-2by026c.jpg",LocalDateTime.now(), new ArrayList<>()));
        pokemones.add(new Pokemon("3","Novillo", "https://notinerd.com/wp-content/uploads/2019/08/2-35.jpg",LocalDateTime.now(), new ArrayList<>()));
        pokemones.add(new Pokemon("4","Carlos", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS4vIVxMfa16WzaQ4IOEXtAacXfV_9phZs65qHvI3fC_kK6kvxpfAHZINoLwUy37_jIVZk&usqp=CAU",LocalDateTime.now(), new ArrayList<>()));
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

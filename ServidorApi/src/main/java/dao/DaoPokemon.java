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
        pokemones.add(new Pokemon("1", "Oscar", "https://i.pinimg.com/236x/57/d9/d8/57d9d8b0315630360590a4dfd57ef17a--cartoon-people-cartoon-characters.jpg", LocalDateTime.now(),
                new ArrayList<>(List.of(new Move("1", "Poner un ceraco", "Movimiento estrella de Oscar, " +
                        "el cual, mediante un sutil movimiento de mano te hunde en la miseria mas absoluta. " +
                        "Especialmente eficaz contra el alumnado.")))));
        pokemones.add(new Pokemon("2", "Stefan", "https://l450v.alamy.com/450ves/2by026c/graciosos-personajes-2by026c.jpg", LocalDateTime.now()
                , new ArrayList<>(List.of(new Move("2","Noche en vela", "Tu oponente se queda con un profundo, " +
                "insomnio durante tres noches seguidas. Ganas tareas acabadas pero pierdes cordura.")))));
        pokemones.add(new Pokemon("3", "Novillo", "https://notinerd.com/wp-content/uploads/2019/08/2-35.jpg", LocalDateTime.now(), new ArrayList<>()));
        pokemones.add(new Pokemon("4", "Carlos", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS4vIVxMfa16WzaQ4IOEXtAacXfV_9phZs65qHvI3fC_kK6kvxpfAHZINoLwUy37_jIVZk&usqp=CAU", LocalDateTime.now()
                , new ArrayList<>(List.of(new Move("2","Noche en vela", "Tu oponente se queda con un profundo, " +
                "insomnio durante tres noches seguidas. Ganas tareas acabadas pero pierdes cordura.")))));
        pokemones.add(new Pokemon("5", "bot5", "https://cdn.pixabay.com/photo/2013/07/13/13/41/robot-161367_960_720.png", LocalDateTime.now(), new ArrayList<>()));
        pokemones.add(new Pokemon("6", "bot6", "https://cdn.pixabay.com/photo/2013/07/13/13/41/robot-161367_960_720.png", LocalDateTime.now(), new ArrayList<>()));
        pokemones.add(new Pokemon("7", "bot7", "https://cdn.pixabay.com/photo/2013/07/13/13/41/robot-161367_960_720.png", LocalDateTime.now(), new ArrayList<>()));
        pokemones.add(new Pokemon("8", "bot8", "https://cdn.pixabay.com/photo/2013/07/13/13/41/robot-161367_960_720.png", LocalDateTime.now(), new ArrayList<>()));
        pokemones.add(new Pokemon("9", "bot9", "https://cdn.pixabay.com/photo/2013/07/13/13/41/robot-161367_960_720.png", LocalDateTime.now(), new ArrayList<>()));
        pokemones.add(new Pokemon("10", "bot10", "https://cdn.pixabay.com/photo/2013/07/13/13/41/robot-161367_960_720.png", LocalDateTime.now(), new ArrayList<>()));
    }

    public DaoPokemon() {

    }

    public Either<ApiError, Pokemon> getPokemon(String id) {
        Pokemon p = pokemones.stream()
                .filter(pokemon -> pokemon.getId().equals(id))
                .findFirst().orElse(null);
        if (p != null) {
            return Either.right(p);
        } else {
            return Either.left(new ApiError("error not found", LocalDateTime.now()));
        }
    }

    public List<Pokemon> getAll() {
        if (pokemones.size() == 0) {
            throw new CustomException("lista vacia", Response.Status.NOT_FOUND);
        }
        return pokemones;
    }

    public Pokemon addPokemon(Pokemon pokemon) {
        int id = Integer.parseInt(pokemones.get(pokemones.size() - 1).getId()) + 1;
        pokemon.setId(String.valueOf(id));
        pokemones.add(pokemon);
        return pokemon;
    }

    public boolean borrarPokemon(String id) {
        return pokemones.remove(pokemones.stream()
                .filter(pokemon -> pokemon.getId().equals(id))
                .findFirst().orElse(null));
    }

    public Pokemon actualizarPokemon(Pokemon p) {
        int id = pokemones.indexOf(p);
        return pokemones.set(id, p);
    }
}

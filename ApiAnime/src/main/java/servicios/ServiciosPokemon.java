package servicios;

import dao.DaoPokemons;
import dao.modelo.Pokemoneh.ResultsItem;

import java.util.List;

public class ServiciosPokemon {
    DaoPokemons daoPokemons = new DaoPokemons();

    public String getAll(String id) {
        return daoPokemons.getAll(id);
    }

    public List<ResultsItem> getAllPokemon() {
        return daoPokemons.getAllPokemon();
    }
}

package servicios;

import dao.DaoPokemons;
import dao.modelo.ModPokemon.Pokemon;
import dao.modelo.ModPokemon.ResultsItem;

import java.util.List;

public class ServiciosPokemon {
    DaoPokemons daoPokemons = new DaoPokemons();

    public Pokemon getDatosByNombre(String id) {
        return daoPokemons.getDatosByNombre(id);
    }

    public List<ResultsItem> getAllPokemon() {
        return daoPokemons.getAllPokemon();
    }
}

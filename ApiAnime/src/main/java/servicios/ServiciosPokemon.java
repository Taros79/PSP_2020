package servicios;

import dao.DaoPokemons;

public class ServiciosPokemon {

    public String getAll() {
        DaoPokemons daoPokemons = new DaoPokemons();
        return daoPokemons.getAll();
    }
}

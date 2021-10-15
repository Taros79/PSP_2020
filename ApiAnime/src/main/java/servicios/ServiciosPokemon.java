package servicios;

import dao.DaoPokemons;
import dao.modelo.ModMovimientos.Movimiento;
import dao.modelo.ModPokemon.MovesItem;
import dao.modelo.ModPokemon.Pokemon;
import dao.modelo.ModPokemon.ResultsItem;

import java.util.List;

public class ServiciosPokemon {
    DaoPokemons daoPokemons = new DaoPokemons();

    public Pokemon getDatosByNombre(String id) {
        return daoPokemons.getDatosByNombre(id);
    }

    public List<MovesItem> getMovimientosPorId(String id) {
        return daoPokemons.getMovimientosPorId(id);
    }

    public List<ResultsItem> getAllPokemon() {
        return daoPokemons.getAllPokemon();
    }

    public Movimiento getDatosMovimiento(String id) {
        return daoPokemons.getDatosMovimiento(id);
    }
}

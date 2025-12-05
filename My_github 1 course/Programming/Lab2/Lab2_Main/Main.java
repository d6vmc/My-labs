package Lab2_Main;

import MyPokemons.*;
import ru.ifmo.se.pokemon.Battle;
import ru.ifmo.se.pokemon.Pokemon;

public class Main {
    // ://pokemondb.net/pokedex/regirock
    // https://pokemondb.net/pokedex/clamperl
    // https://pokemondb.net/pokedex/huntail
    // https://pokemondb.net/pokedex/chimchar
    // https://pokemondb.net/pokedex/monferno
    // https://pokemondb.net/pokedex/infernape

    public static void main(String[] args){
        Battle b = new Battle();

        Regirock regirock = new Regirock("Dungeon Master", 1);
        Clamperl clamperl = new Clamperl("Nefor", 1);
        Chimchar chimchar  = new Chimchar("Lox", 1);
        Huntail huntail = new Huntail("Glist", 1);
        Infernape infernape = new Infernape("Makaka", 1);
        Monferno monferno = new Monferno("Degenerat", 1);

        b.addAlly(clamperl);
        b.addAlly(chimchar);
        b.addAlly(monferno);

        b.addFoe(huntail);
        b.addFoe(infernape);
        b.addFoe(regirock);
        b.go();
    }
    public static boolean chance(double d) {
        return d > Math.random();
    }

}
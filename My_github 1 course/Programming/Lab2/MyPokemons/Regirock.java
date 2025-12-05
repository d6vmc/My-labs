package MyPokemons;

import MyMoves.Regirock.*;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Regirock extends Pokemon {
    public Regirock(String name, int level) {
        super(name, level);

        super.setType(Type.ROCK);
        super.setStats(80, 100, 200, 50, 100, 50);

        Thunderbolt thunderbolt = new Thunderbolt(90, 100);
        RockSlide rockSlide = new RockSlide(75, 90);
        StoneEdge stoneEdge = new StoneEdge(100, 80);
        AncientPower ancientPower = new AncientPower(60, 100);

        super.setMove(thunderbolt, rockSlide, stoneEdge, ancientPower);

    }
}

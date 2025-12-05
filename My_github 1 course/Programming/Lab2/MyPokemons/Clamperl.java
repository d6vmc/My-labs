package MyPokemons;

import MyMoves.Clamperl.*;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Clamperl extends Pokemon {
    public Clamperl(String name, int level) {
        super(name, level);

        super.setType(Type.WATER);
        super.setStats(35, 64, 85, 74, 55, 32);

        Blizzard blizzard = new Blizzard(110, 70);
        Swagger swagger = new Swagger(85, 15);
        Scald scald = new Scald(80,100);

        super.setMove(blizzard, swagger, scald);
    }
}

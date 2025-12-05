package MyPokemons;

import MyMoves.Huntail.*;
import MyMoves.Clamperl.*;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Huntail extends Clamperl {
    public Huntail(String name, int level) {
        super(name, level);
        super.setType(Type.WATER);
        super.setStats(55,104,105,94,75,52);

        IceFang iceFang = new IceFang(65, 95);
        Blizzard blizzard = new Blizzard(110, 70);
        Swagger swagger = new Swagger(85, 15);
        Scald scald = new Scald(80,100);

        super.setMove(blizzard, swagger, scald, iceFang);

    }
}

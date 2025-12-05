package MyPokemons;

import MyMoves.Chimchar.*;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Chimchar extends Pokemon {
    public Chimchar(String name, int level) {
        super(name, level);
        super.setType(Type.FIRE);
        super.setStats(44, 58, 44, 58, 44, 61);

        WorkUp workUp = new WorkUp(0, 0);
        Ember ember = new Ember(40, 100);

        super.setMove(workUp, ember);
    }
}

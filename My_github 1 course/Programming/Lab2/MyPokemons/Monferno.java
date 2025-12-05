package MyPokemons;

import MyMoves.Chimchar.Ember;
import MyMoves.Chimchar.WorkUp;
import MyMoves.Monferno.SlackOff;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Monferno extends Chimchar {
    public Monferno(String name, int level) {
        super(name, level);
        super.setType(Type.FIRE, Type.FIGHTING);
        super.setStats(64, 78, 52, 78, 52, 81);

        SlackOff slackOff = new SlackOff(0, 0);
        WorkUp workUp = new WorkUp(0, 0);
        Ember ember = new Ember(40, 100);

        super.setMove(slackOff, workUp, ember);
    }
}

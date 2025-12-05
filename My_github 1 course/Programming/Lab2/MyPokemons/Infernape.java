package MyPokemons;

import MyMoves.Chimchar.Ember;
import MyMoves.Chimchar.WorkUp;
import MyMoves.Infernape.CalmMind;
import MyMoves.Monferno.SlackOff;
import ru.ifmo.se.pokemon.Type;

public class Infernape extends Monferno{
    public Infernape(String name, int level) {
        super(name, level);
        super.setType(Type.FIRE, Type.FIGHTING);
        super.setStats(76,104,71,104,71,108);

        CalmMind calmMind = new CalmMind(0,0);
        SlackOff slackOff = new SlackOff(0, 0);
        WorkUp workUp = new WorkUp(0, 0);
        Ember ember = new Ember(40, 100);

        super.setMove(calmMind, slackOff, workUp, ember);
    }
}

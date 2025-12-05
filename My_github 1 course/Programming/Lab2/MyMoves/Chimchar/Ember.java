package MyMoves.Chimchar;

import Lab2_Main.Main;
import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

public class Ember extends SpecialMove {
    public Ember(double pow, double acc){
        super(Type.FIRE, pow, acc);
    }
    @Override
    protected void applyOppEffects(Pokemon p) {
        super.applyOppEffects(p);

        if (Main.chance(0.1)) {
            Effect.burn(p);
        }
    }

    protected String describe() {
        return "Uses Ember spell";
    }
}

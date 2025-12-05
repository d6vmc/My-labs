package MyMoves.Clamperl;

import Lab2_Main.Main;
import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

public class Scald extends SpecialMove {
    public Scald (double pow, double acc){
        super(Type.WATER, pow, acc);
    }
    @Override
    protected void applyOppEffects(Pokemon p) {
        super.applyOppEffects(p);
        if (Main.chance(0.3)) {
            Effect.burn(p);
        }
    }

    protected String describe() {
        return "Uses Scald";
    }
}

package MyMoves.Regirock;

import Lab2_Main.Main;
import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class RockSlide extends PhysicalMove {
    public RockSlide(double pow, double acc) {
        super(Type.ROCK, pow, acc);
    }
    @Override
    protected void applyOppEffects(Pokemon p) {
        super.applyOppEffects(p);

        if (Main.chance(0.3)) {
            Effect.flinch(p);
        }
    }

    protected String describe() {
        return "Uses RockSlide";
    }
}

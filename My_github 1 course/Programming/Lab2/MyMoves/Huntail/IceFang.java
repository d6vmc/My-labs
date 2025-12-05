package MyMoves.Huntail;

import Lab2_Main.Main;
import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class IceFang extends PhysicalMove {
    public IceFang(double pow, double acc){
        super(Type.ICE, pow, acc);
    }
    @Override
    protected void applyOppEffects(Pokemon p) {
        super.applyOppEffects(p);
        if (Main.chance(0.1)) {
            Effect.freeze(p);
        }

        if (Main.chance(0.1)) {
            Effect.flinch(p);
        }
    }

    protected String describe() {
        return "Uses IceFang";
    }
}

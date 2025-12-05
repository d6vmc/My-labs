package MyMoves.Infernape;

import ru.ifmo.se.pokemon.*;

public class CalmMind extends StatusMove {
    public CalmMind(double pow, double acc){
        super(Type.PSYCHIC, pow, acc);
    }
    @Override
    protected void applySelfEffects(Pokemon p) {
        super.applySelfEffects(p);

        Effect e = new Effect().stat(Stat.SPECIAL_DEFENSE, 1);
        Effect m = new Effect().stat(Stat.SPECIAL_ATTACK, 1);

        p.addEffect(e);
        p.addEffect(m);
    }

    protected String describe() {
        return "Uses Calm Mind";
    }
}

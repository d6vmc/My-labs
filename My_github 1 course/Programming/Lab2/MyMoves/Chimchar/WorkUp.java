package MyMoves.Chimchar;

import ru.ifmo.se.pokemon.*;

public class WorkUp extends StatusMove {
    public WorkUp(double pow, double acc){
        super(Type.NORMAL, pow, acc);
    }
    @Override
    protected void applySelfEffects(Pokemon p) {
        super.applySelfEffects(p);

        Effect e = new Effect().stat(Stat.ATTACK, 1);
        Effect m = new Effect().stat(Stat.SPECIAL_ATTACK, 1);

        p.addEffect(e);
        p.addEffect(m);
    }
    protected String describe() {
        return "Uses WorkUp";
    }
}

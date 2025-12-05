package MyMoves.Monferno;

import ru.ifmo.se.pokemon.*;

public class SlackOff extends StatusMove {
    public SlackOff(double pow, double acc){
        super(Type.NORMAL, pow, acc);
    }
    @Override
    protected void applySelfEffects(Pokemon p) {
        super.applySelfEffects(p);

        Effect e = new Effect().stat(Stat.HP, (int) (p.getHP()*0.5));

        p.addEffect(e);
    }

    protected String describe() {
        return "uses Slack off";
    }
}

package MyMoves.Regirock;

import Lab2_Main.Main;
import ru.ifmo.se.pokemon.*;

public class AncientPower extends SpecialMove {
    public AncientPower(double pow, double acc){
        super(Type.ROCK, pow, acc);
    }

    @Override
    protected void applySelfEffects(Pokemon p){
        super.applySelfEffects(p);
        Effect e = new Effect().stat(Stat.ATTACK, 1);
        Effect a = new Effect().stat(Stat.DEFENSE, 1);
        Effect b = new Effect().stat(Stat.SPECIAL_ATTACK, 1);
        Effect n = new Effect().stat(Stat.SPECIAL_DEFENSE, 1);
        Effect v = new Effect().stat(Stat.SPEED, 1);

        if (Main.chance(0.1)) {
            p.addEffect(e);
            p.addEffect(a);
            p.addEffect(b);
            p.addEffect(n);
            p.addEffect(v);
        }
    }

    protected String describe() {
        return "Uses Ancient Power";
    }
}

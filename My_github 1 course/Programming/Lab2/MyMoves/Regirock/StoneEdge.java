package MyMoves.Regirock;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class StoneEdge extends PhysicalMove {
    public StoneEdge(double pow, double acc) {
        super(Type.ROCK, pow, acc);
    }
    protected String describe() {
        return "Uses Stone Edge";
    }
}

package Entities;

public interface Creature {

    void TakeDamage(int amount);

    boolean isAlive();

    String getName();

    void turn(Creature target);

}

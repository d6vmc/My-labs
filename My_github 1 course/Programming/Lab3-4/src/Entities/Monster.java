package Entities;

public class Monster implements Creature {
    protected String name;
    protected int health;
    protected int attack_power;
    protected int max_health;

    public Monster(String name, int health, int attack_power) {
        this.name = name;
        this.health = health;
        this.max_health = health;
        this.attack_power = attack_power;

        System.out.println("Появился монстр " + this.name + " с силой атаки " + this.attack_power + " и здоровьем " + this.health);
    }

    @Override
    public void turn(Creature target) {
        System.out.println(this.name + "наносит урон по " + target.getName() + "!!!");
        target.TakeDamage(this.attack_power);
    }

    @Override
    public void TakeDamage(int amount) {
        this.health -= amount;

        if (health < 0) {
            health = 0;
        }

        System.out.println(this.name + " получил " + amount + " урона. Осталось здоровья: " + this.health);
        if (!isAlive()) {
            System.out.println(this.name + " был побежден");
        }
    }

    @Override
    public boolean isAlive() {
        return this.health > 0;
    }
    @Override
    public String getName() {
        return this.name;
    }
}
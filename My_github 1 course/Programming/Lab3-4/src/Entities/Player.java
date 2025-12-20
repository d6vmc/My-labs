package Entities;

import java.util.Scanner;

public class Player implements Creature {
    private String name;
    private int health;
    private int attack_power;
    private int max_health;
    private double armor;

    private static Scanner scanner = new Scanner(System.in);

    public Player(String name, int health, int attack_power, double armor){
        this.name = name;
        this.max_health = health;
        this.health = health;
        this.attack_power = attack_power;
        this.armor = armor;

        System.out.println("Создан герой " + this.name + " с силой атаки " + this.attack_power + ", здоровьем " + this.health + " и устойчивостью к урону " + this.armor * 100+ " процентов(броня)");
    }

    @Override
    public void turn(Creature target) {
        System.out.println("Выберете действие:");
        System.out.println("1. Обычная атака (Урон: " + this.attack_power + ")");
        System.out.println("2. Лечение (Восстановить 20 Hp)");

        int choice = 0;

        if (scanner.hasNextInt()){
            choice = scanner.nextInt();
        }
        else {
            scanner.next();
        }
        switch (choice) {
            case 1:
                performBasicAttack(target);
                break;
            case 2:
                performHeal();
                break;
            default:
                System.out.println("Неверный выбор, вы растерялись и пропускаете ход!");
                break;
        }

    }

    private void performBasicAttack(Creature target) {
        System.out.println(this.name + " наносит обычный удар по " + target.getName() + "!!!");
        target.TakeDamage(this.attack_power);
    }

    public void attk_increase(int damageIncrease) {
        this.attack_power += damageIncrease;
        System.out.println("Ваша сила атаки выросла, теперь она: " + this.attack_power);
    }
    public void armour_increase(double armorIncrease) {
        this.armor += armorIncrease;
        System.out.println("Ваш показатель защиты вырос, теперь он " + this.armor * 100 + "%");
    }

    public int attk_value() {
        return this.attack_power;
    }

    public void performHeal() {
        int HealAmount = 20;
        this.health += HealAmount;
        if (this.health > this.max_health) {
            this.health = this.max_health;
        }
        System.out.println(this.name + " лечит себя на " + HealAmount + "Hp, текущее здоровье: " + this.health);
    }

    public void AbsHeal() {
        this.health = this.max_health;
        System.out.println("Вы герой " + this.name + " с силой атаки " + this.attack_power + ", здоровьем " + this.health + " и устойчивостью к урону " + this.armor * 100+ " процентов(броня)");
    }

    @Override
    public void TakeDamage(int amount) {
        this.health -= (int) Math.round(amount * (1-this.armor));

        if (health < 0) {
            health = 0;
        }

        System.out.println(this.name + " получил " + amount + " урона, броня приняла часть вреда на себя. Осталось здоровья: " + this.health);

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

    private int BossesDefeated = 0;

    public int GetBossesDefeated() {
        return BossesDefeated;
    }

    public void plusBossesDefeated() {
        this.BossesDefeated += 1;
        System.out.println("Ваш счетчик боссов обновлен: " + BossesDefeated);
    }
}

package Journeys;
import Entities.*;
import Bosses.*;

public class BossFight {
    private Creature hero;
    private Monster boss;

    public BossFight(Creature hero, Monster monster) {
        this.hero = hero;
        this.boss = monster;
    }

    public void Start_BossFight() {
        System.out.println("----ВЫ ПРИЗВАЛИ БОССА " + boss.getName() + "----");
        System.out.println("--- БОСС ФАЙТ НАЧИНАЕТСЯ! ---");
        System.out.println(hero.getName() + " против " +  boss.getName());
        System.out.println("--------------------------");
        while (hero.isAlive() && boss.isAlive()) {
            hero.turn(boss);
            if (!boss.isAlive()) {
                break;
            }
            hero.turn(boss);
            if (!boss.isAlive()) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boss.turn(hero);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (hero.isAlive()) {
            System.out.println(hero.getName() + " ПОБЕДИЛ БОССА " + boss.getName());
        }
        else {
            System.out.println(boss.getName() + " УНИЗИЛ ГЕРОЯ " + hero.getName());
        }
    }
}


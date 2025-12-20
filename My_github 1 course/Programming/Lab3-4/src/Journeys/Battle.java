package Journeys;
import Entities.*;

public class Battle {
    private Creature hero;
    private Creature monster;

    public Battle(Creature hero, Creature monster) {
        this.hero = hero;
        this.monster = monster;
    }

    public void Start_battle() {
        System.out.println("--- Битва начинается! ---");
        System.out.println(hero.getName() + " против " + monster.getName());
        System.out.println("--------------------------");
        while (hero.isAlive() && monster.isAlive()) {
            hero.turn(monster);
            if (!monster.isAlive()) {
                break;
            }
            hero.turn(monster);
            if (!monster.isAlive()) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            monster.turn(hero);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (hero.isAlive()) {
            System.out.println(hero.getName() + " выиграл в бою с " + monster.getName());
        }
        else {
            System.out.println(monster.getName() + " выиграл в бою с " + hero.getName());
        }
    }
}

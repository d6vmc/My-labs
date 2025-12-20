package Bosses;

import Entities.*;
import java.util.Random;

public class MoonLord extends Monster{
    String RED = "\u001B[31m";
    String RESET = "\u001B[0m";
    private boolean isSecondStageActivated = false;

    public MoonLord() {
        super("Лунный Лорд", 900, 80);
    }

    @Override
    public void turn(Creature target) {
        if (this.health < (this.max_health / 2) && !isSecondStageActivated) {
            second_stage();
            return;
        }

        Random rand = new Random();
        int chance = rand.nextInt(100);

        if (isSecondStageActivated) {
            int chance_avoidance = 10;
            if (chance < chance_avoidance) {
                System.out.println("ВЫ УВЕРНУЛИСЬ ОТ АТАКИ " + name);
            } else if (chance >= chance_avoidance && chance < 60) {
                ramming_2stage(target);
            }
            else{
                base_attk_2stage(target);
            }
        }
        else {
            int chance_avoidance = 30;
            if (chance < chance_avoidance) {
                System.out.println("ВЫ УВЕРНУЛИСЬ ОТ АТАКИ " + name);
            } else if (chance >= chance_avoidance && chance < 60) {
                ramming(target);
            }
            else{
                base_attk(target);
            }
        }
    }

    public void base_attk(Creature target) {
        System.out.println(this.name + " Выпускает сверхмощные лазеры из глаз на " + target.getName() + "!!!");
        target.TakeDamage(this.attack_power);
    }

    public void base_attk_2stage(Creature target) {
        System.out.println(this.name + " Бесчисленные лазеры направлены на " + target.getName() + "!!!");
        target.TakeDamage(this.attack_power*2);
    }

    public  void ramming(Creature target) {
        System.out.println(this.name + " Направляет свои огромные конечности на " + target.getName() + "!!");
        target.TakeDamage(this.attack_power);
    }

    public  void ramming_2stage(Creature target) {
        System.out.println(this.name + " Бесчисленные руки направлены на " + target.getName() + "!!");
        target.TakeDamage(this.attack_power*2);
    }

    public void second_stage() {
        System.out.println(RED + "БОСС ВХОДИТ ВО 2 СТАДИЮ, появляются новые атаки, урон Мунлорда возрастает, шанс уклонения понижается" + RESET);
        isSecondStageActivated = true;
    }
}

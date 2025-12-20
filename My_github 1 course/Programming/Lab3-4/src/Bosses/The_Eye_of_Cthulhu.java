package Bosses;

import Entities.*;
import java.util.Random;

public class The_Eye_of_Cthulhu extends Monster{
    String RED = "\u001B[31m";
    String RESET = "\u001B[0m";
    private boolean isSecondStageActivated = false;

    public The_Eye_of_Cthulhu() {
        super("Глаз Ктулху", 200, 30);

    }

    @Override
    public void turn(Creature target) {
        if (this.health < (this.max_health / 2) && !isSecondStageActivated) {
            second_stage();
            return;
        }

        Random rand = new Random();
        int chance = rand.nextInt(100);

        if (chance < 30) {
            System.out.println("ВЫ УВЕРНУЛИСЬ ОТ АТАКИ " + name);
        } else if (chance >= 30 && chance < 60) {
            ramming(target);
        }else{
            base_attk(target);
        }

    }

    public void base_attk(Creature target) {
        System.out.println(this.name + " кусает " + target.getName() + "!!!");
        target.TakeDamage(this.attack_power);
    }

    public  void ramming(Creature target) {
        System.out.println(this.name + " Таранит " + target.getName() + "!!");
        target.TakeDamage(this.attack_power);
    }

    public void second_stage() {
        System.out.println(RED + "БОСС ВХОДИТ ВО 2 СТАДИЮ, СИЛА АТАКИ ПОВЫШАЕТСЯ В 2 РАЗА" + RESET);
        this.attack_power = attack_power*2;
        isSecondStageActivated = true;

    }
}

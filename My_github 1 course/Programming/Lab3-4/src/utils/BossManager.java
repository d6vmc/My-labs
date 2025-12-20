package utils;
import Bosses.*;
import Entities.*;

public class BossManager {
    String RED = "\u001B[31m";
    String RESET = "\u001B[0m";

    public Monster SummonNextBoss(int level) {
        switch (level) {
            case 0:
                System.out.println(RED + "Был призван ГЛАЗ КТУЛХУ..." + RESET);
                return new The_Eye_of_Cthulhu();
            case 1:
                System.out.println(RED + "Вы слышите лязг костей и зловещий смех..." + RESET);
                return new Skeletron();
            case 2:
                System.out.println(RED + "Вы слышите завывания душ из глубин ада..." + RESET);
                return new The_Wall_of_Flesh();
            case 3:
                System.out.println(RED + "Земля под вами дрожит и трескается..." + RESET);
                return new The_Devourer_of_Worlds();
            case 4:
                System.out.println(RED + "Вы слишите звуки бесчисленных механизмов..." + RESET);
                return new Skeletron_Prime();
            case 5:
                System.out.println(RED + "Джунгли пробуждаются..." + RESET);
                return new The_Planter();
            case 6:
                System.out.println(RED + "Древняя гробница оживает..." + RESET);
                return new Golem();
            case 7:
                System.out.println(RED + "Наступило солнечное затмение..." + RESET);
                return new MoonLord();
            default:
                return null;
        }
    }
}

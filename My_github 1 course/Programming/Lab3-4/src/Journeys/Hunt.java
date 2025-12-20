package Journeys;

import Entities.Monster;
import Entities.Player;
import utils.VideoPlayer;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Hunt {
    private Player hero;

    public Hunt(Player hero){
        this.hero = hero;
    }
    String RED = "\u001B[31m";
    String RESET = "\u001B[0m";
    String WHITE_BG = "\u001B[47m";

    public void Start_Hunt(){
        Random random = new Random();

        for (int i = 1; i<=5; i++) {
            int time = random.nextInt(15 - 8) + 8;

            for (int j = 0; j < time; j++) {
                try {
                    Thread.sleep(1000);
                    System.out.print(".");
                } catch (InterruptedException e) {
                }
            }
            System.out.println();
            System.out.println("Вы наткнулись на Врага!");
            int chance = random.nextInt(5)+1;
            switch (chance) {
                case 1:
                    Monster Zombie = new Monster("Зомби", 30, 10);
                    Battle battle1 = new Battle(this.hero, Zombie);
                    battle1.Start_battle();
                    break;
                case 2:
                    Monster Slime = new Monster("Слайм", 30, 10);
                    Battle battle2 = new Battle(this.hero, Slime);
                    battle2.Start_battle();
                    break;
                case 3:
                    Monster Demon = new Monster("Демон", 50, 20);
                    System.out.println("Как вы вообще умудрились наткнуться на демона вне ада!");
                    Battle battle3 = new Battle(this.hero, Demon);
                    battle3.Start_battle();
                    break;
                case 4:
                    Monster Demon_eye = new Monster("Демонический глаз", 30, 10);
                    Battle battle4 = new Battle(this.hero, Demon_eye);
                    battle4.Start_battle();
                    break;
                case 5:
                    Monster Garpia = new Monster("Гарпия", 50, 10);
                    Battle battle5 = new Battle(this.hero, Garpia);
                    battle5.Start_battle();
                    break;
            }
            if (!this.hero.isAlive()){
                System.out.println("Вы умерли, пока исследовали этот опасный мир. Вселенная будет скорбить по вам(");
                break;
            }
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Вы нашли НОВЫЙ ПРЕДМЕТ: " + RED + "DEMON SWORD" + RESET);
        if (this.hero.attk_value() < 150) {
            VideoPlayer.playVideo("src/Hunt_base.MP4", 10);
            System.out.println("Вы экипировали " + RED + "DEMON SWORD" + RESET);
            this.hero.attk_increase(20);
        }else {
            VideoPlayer.playVideo("src/Hunt_error.MP4", 10);
            System.out.println("Вы обладаете куда лучшим снаряжением чем это!");
            for (int m = 0; m < 5; m++){
                System.out.println(RED + WHITE_BG +"А МОЖЕТ ЭТО ТЫ, ОШИБКА, ТЫ ЛИ ЭТО?" + RESET);
                try {
                    Thread.sleep(random.nextInt(30) + 5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Вы закончили свое первое путешествие!!");
    }
}

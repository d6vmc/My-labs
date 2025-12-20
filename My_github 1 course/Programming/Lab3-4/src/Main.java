import Entities.*;
import Journeys.*;
import utils.BossManager;
import utils.VideoPlayer;

import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        /// Дальше сюда добавить видео с амоном, что нить еще покрасивее реализовать отдельено все локи и боссов сделать музыку и видосики всякие
        System.out.println("Добро пожаловать в Terraria ITMO EDITION");
        System.out.print("Впишите имя для своего героя: ");
        String heroName = scanner.nextLine();
        int base_health = 100;
        int base_dmg = 50;
        double base_armor = 0.2;
        String RED = "\u001B[31m";
        String RESET = "\u001B[0m";
        String WHITE_BG = "\u001B[47m";
        if (heroName.equals("Man with Monocle")) {
            Random random = new Random();
            String[] errors = {
                    "FATAL ERROR", "SYSTEM CORRUPTION", "0x000F4A",
                    "SEGMENTATION FAULT", "CRITICAL FAILURE", "ACCESS DENIED",
                    "DATA LOSS IMMINENT", "NULL POINTER EXCEPTION"
            };

            System.out.println(RED + "ВЫ ПРИЗВАЛИ ОШИБКУ..." + RESET);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }

            for (int i = 0; i < 300; i++) {
                StringBuilder sb = new StringBuilder();
                while (sb.length() < 180) {
                    int type = random.nextInt(4);
                    switch (type) {
                        case 0:
                            for (int b = 0; b < 5; b++) sb.append(random.nextInt(2));
                            break;
                        case 1:
                            sb.append(" ").append(errors[random.nextInt(errors.length)]).append(" ");
                            break;
                        case 2:
                            sb.append(" 0x").append(Integer.toHexString(random.nextInt(99999)).toUpperCase());
                            break;
                        case 3:
                            sb.append(" --ERROR-- ");
                            break;
                    }
                }
                if (random.nextInt(15) == 0) {
                    System.out.println(WHITE_BG + RED + sb.toString() + RESET);
                } else {
                    System.out.println(RED + sb.toString() + RESET);
                }
                try {
                    Thread.sleep(random.nextInt(30) + 5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            base_health = 1000000;
            base_dmg = 10000;
        }
        Player hero = new Player(heroName, base_health, base_dmg, base_armor);
        boolean gameRunning = true;
        while (gameRunning && hero.isAlive()) {
            System.out.println("---------Главное меню---------");
            System.out.println("1. Похождения по миру и охота на монстров");
            System.out.println("2. Добыча ресурсов");
            System.out.println("3. Охота на Боссов");
            System.out.println("4. Проверить свой инвентарь(броню и оружие)");
            System.out.println("5. Выход из Terraria ITMO EDITION");
            System.out.print("Выбор: ");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        Hunt hunt = new Hunt(hero);
                        hunt.Start_Hunt();
                        break;
                    case 2:
                        Mining mining = new Mining(hero);
                        mining.Start_Mining();
                        break;
                    case 3:
                        BossManager bossManager = new BossManager();
                        int currentLevel = hero.GetBossesDefeated();
                        Monster boss = bossManager.SummonNextBoss(currentLevel);
                        if (boss != null) {
                            BossFight bossFight = new BossFight(hero, boss);
                            bossFight.Start_BossFight();
                            if (hero.isAlive()) {
                                String videoName = "";
                                if (currentLevel == 2){
                                    if (heroName.equals("Man with Monocle")) {
                                        videoName = "src/Error_Flesh.MP4";
                                        VideoPlayer.playVideo(videoName, 10);
                                    }
                                    else{
                                        videoName = "src/Base_Flesh.MP4";
                                        VideoPlayer.playVideo(videoName, 10);
                                    }
                                }
                                else if (currentLevel == 7) {
                                    if (heroName.equals("Man with Monocle")) {
                                        videoName = "src/Error_Moon.MP4";
                                        VideoPlayer.playVideo(videoName, 10);
                                    } else {
                                        videoName = "src/Base_Moon.MP4";
                                        VideoPlayer.playVideo(videoName, 10);
                                    }
                                }
                                hero.plusBossesDefeated();
                                System.out.println(WHITE_BG + RED + "Победа! Следующий Босс доступен" + RESET);
                                hero.AbsHeal();
                                System.out.println("Ваше здоровье полностью восстановлено.");
                            }
                        }else {
                            System.out.println(WHITE_BG + RED + " ВЫ УНИЧТОЖИЛИ ВСЕХ БОГОВ ЭТОГО МИРА! " + RESET);
                            System.out.println(WHITE_BG + RED + "Теперь вы — абсолютный правитель Terraria ITMO EDITION."+ RESET);
                        }
                        break;
                    case 4:
                        throw new UnsupportedOperationException("Функция находится в разработке");
                    case 5:
                        gameRunning = false;
                        break;
                }
            } else {
                scanner.next();
            }
        }
    }
}
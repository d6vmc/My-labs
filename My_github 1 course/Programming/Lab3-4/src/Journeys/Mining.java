package Journeys;

import Entities.Player;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import Entities.*;
import Bosses.*;
import utils.VideoPlayer;

public class Mining {
    private Player hero;

    public Mining(Player hero) {
        this.hero = hero;
    }

    String RED = "\u001B[31m";
    String RESET = "\u001B[0m";
    String WHITE_BG = "\u001B[47m";

    public void Start_Mining() {
        boolean isError = this.hero.getName().trim().equals("Man with Monocle");

        String videoName;

        if (isError) {
            System.out.println(WHITE_BG + RED + " ВЫ НАЧАЛИ ДОБЫЧУ РЕСУРСОВ! " + RESET);
            System.out.println(WHITE_BG + RED + "КТО ЖЕ К НАМ ПОЖАЛОВАЛ, ОШИБКА, ТЫ ЛИ ЭТО!" + RESET);
            videoName = "src/Mining_error.MP4";
        } else {
            System.out.println(WHITE_BG + RED + " ВЫ НАЧАЛИ ДОБЫЧУ РЕСУРСОВ! " + RESET);
            videoName = "src/Mining_base.MP4";
        }
        VideoPlayer.playVideo(videoName, 10);

        if (isError) {
            System.out.println("\n" + RED + "Добыча завершена!" + RESET);
            System.out.println("Вы выковали предмет: " + RED + "БРОНЯ ДЬЯВОЛА" + RESET);
            System.out.println(RED + WHITE_BG + "Дьявол носит броню дьявола, как иронично, ААХАХАХАХААХАХ" + RESET);
            System.out.println("Вы экипировали предмет: " + RED + "БРОНЯ ДЬЯВОЛА" + RESET);
            this.hero.armour_increase(0.5);
        } else {
            System.out.println("\n" + RED + "Добыча завершена!" + RESET);
            System.out.println("Вы выковали предмет: " + RED + "Сияющая броня!" + RESET);
            System.out.println("Вы экипировали предмет: " + RED + "Сияющая броня" + RESET);
            this.hero.armour_increase(0.3);
        }
    }
}

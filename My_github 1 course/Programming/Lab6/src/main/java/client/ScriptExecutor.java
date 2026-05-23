package client;

import common.network.Request;
import common.network.Response;

import java.io.File;
import java.util.Scanner;
import java.util.Set;

public class ScriptExecutor {

    public static void executeScript(String line, RequestBuilder builder, UdpClient udpClient, Set<String> activeScripts) {
        String[] parts = line.trim().split("\\s+");

        if (parts.length != 2) {
            System.out.println("Использование: execute_script <file>");
            return;
        }
        File file = new File(parts[1]).getAbsoluteFile();
        String path = file.getAbsolutePath();

        if (activeScripts.contains(path)) {
            System.out.println("Обнаружена рекурсия в execute_script");
            return;
        }

        activeScripts.add(path);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String commandLine = scanner.nextLine().trim();
                if (commandLine.isEmpty()) {
                    continue;
                }
                System.out.println("> " + commandLine);

                if (commandLine.startsWith("execute_script")) {
                    executeScript(commandLine, builder, udpClient, activeScripts);
                    continue;
                }
                Request request = builder.build(commandLine);
                Response response = udpClient.sendRequest(request);
                if (response.message() != null) {
                    System.out.println(response.message());
                }
                if (response.exit()) {
                    break;
                }
            }
            System.out.println("Скрипт выполнен");
        } catch (Exception e) {
            System.out.println("Ошибка execute_script: " + e.getMessage());
        } finally {
            activeScripts.remove(path);
        }
    }
}
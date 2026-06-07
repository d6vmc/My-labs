package client;

import common.CommandType;
import common.network.Request;
import common.network.Response;
import common.network.Serializer;
import common.validation.Validator;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static client.ScriptExecutor.executeScript;

public class ClientMain {
    public static void main(String[] args) {
        Validator validator = new Validator();
        Scanner scanner = new Scanner(System.in);
        InputManager input = new InputManager(scanner);
        InputReader reader = new InputReader();
        PersonReader personReader = new PersonReader(input, reader, validator);
        Serializer serializer = new Serializer();
        UdpClient udpClient;
        Set<String> activeScripts = new HashSet<>();
        try {
            udpClient = new UdpClient("localhost", 5555, serializer);
        } catch (Exception e) {
            System.out.println("Не удалось создать udp клиент: " + e.getMessage());
            return;
        }
        String login;
        String password;
        while (true) {
            System.out.println("1 — Войти");
            System.out.println("2 — Зарегистрироваться");
            String mode = input.readLine("> ");
            if (mode == null) {
                System.out.println("Завершение программы");
                return;
            }
            mode = mode.trim();
            if (!mode.equals("1") && !mode.equals("2")) {
                System.out.println("Ошибка: выберите 1 или 2");
                continue;
            }
            login = input.readLine("Логин: ");
            password = readPassword(input);
            if (login == null || login.isBlank() || password == null || password.isBlank()) {
                System.out.println("Ошибка: логин и пароль не могут быть пустыми");
                continue;
            }
            CommandType authCommand = mode.equals("1")
                    ? CommandType.LOGIN
                    : CommandType.REGISTER;
            Request authRequest = new Request(login, password, authCommand, null);
            try {
                Response authResponse = udpClient.sendRequest(authRequest);
                if (authResponse.message() != null) {
                    System.out.println(authResponse.message());
                }
                if (authResponse.success()) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Ошибка авторизации: " + e.getMessage());
            }
        }
        RequestBuilder builder = new RequestBuilder(personReader, reader, input, login, password);
        while (true) {
            String line = input.readLine("~ ");
            if (line == null) {
                break;
            }
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            if (line.startsWith("execute_script")) {
                executeScript(line, builder, udpClient, activeScripts);
                continue;
            }
            try {
                Request request = builder.build(line);
                if (request.commandType() == CommandType.EXIT) {
                    System.out.println("Завершение программы");
                    break;
                }
                Response response = udpClient.sendRequest(request);
                if (response.message() != null) {
                    System.out.println(response.message());
                }

                if (response.exit()) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }
    private static String readPassword(InputManager input) {
        java.io.Console console = System.console();
        if (console != null) {
            char[] passwordChars = console.readPassword("Пароль: ");
            if (passwordChars == null) {
                return null;
            }
            return new String(passwordChars);
        }
        return input.readLine("Пароль: ");
    }
}
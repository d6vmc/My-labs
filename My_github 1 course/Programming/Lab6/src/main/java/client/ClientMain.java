package client;

import common.CommandType;
import common.network.*;
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
        RequestBuilder builder = new RequestBuilder(personReader, reader, input);
        Serializer serializer = new Serializer();
        UdpClient udpClient;
        Set<String> activeScripts = new HashSet<>();
        try {
            udpClient = new UdpClient("185.186.142.58", 5555, serializer);
        } catch (Exception e) {
            System.out.println("Не удалось создать udp клиент: " + e.getMessage());
            return;
        }

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
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
                e.printStackTrace();
            }

        }
    }
}

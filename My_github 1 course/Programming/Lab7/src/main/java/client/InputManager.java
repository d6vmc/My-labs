package client;

import java.util.Scanner;
import java.util.Objects;


/**
 * Класс для управления вводом данных из разных источников.
 *
 * Позволяет переключаться между вводом из консоли и вводом из файла (скрипта).
 * Используется для поддержки интерактивного режима и выполнения команд из файлов.
 *
 * В зависимости от режима, чтение осуществляется либо через консольный Scanner,
 * либо через Scanner, связанный с файлом.
 *
 * @author Эльдар
 * @version 1.0
 */
public class InputManager {

    /** Сканер для чтения из консоли */
    private final Scanner consoleScanner;

    /** Текущий активный сканер (консоль или скрипт) */
    private Scanner currentScanner;

    /** Флаг интерактивного режима (true — консоль, false — скрипт) */
    private boolean interactive = true;

    /**
     * Создаёт менеджер ввода с заданным консольным сканером.
     *
     * @param consoleScanner сканер для чтения из консоли
     * @throws NullPointerException если consoleScanner равен null
     */
    public InputManager(Scanner consoleScanner) {
        this.consoleScanner = Objects.requireNonNull(consoleScanner, "consoleScanner не должен быть null");
        this.currentScanner = this.consoleScanner;
        this.interactive = true;
    }

    /**
     * Переключает источник ввода на скрипт (файл).
     *
     * @param scriptScanner сканер, связанный с файлом-скриптом
     * @throws NullPointerException если scriptScanner равен null
     */
    public void useScript(Scanner scriptScanner) {
        this.currentScanner = Objects.requireNonNull(scriptScanner, "scriptScanner не должен быть null");
        this.interactive = false;
    }

    /**
     * Возвращает ввод обратно в консольный режим.
     */
    public void useConsole() {
        this.currentScanner = this.consoleScanner;
        this.interactive = true;
    }

    /**
     * Проверяет, находится ли программа в интерактивном режиме.
     *
     * @return true, если ввод осуществляется из консоли, иначе false
     */
    public boolean isInteractive() {
        return interactive;
    }

    /**
     * Считывает строку из текущего источника ввода.
     *
     * В интерактивном режиме выводит приглашение (prompt).
     *
     * @param prompt строка-приглашение (например "~ "), может быть null
     * @return считанная строка или null, если ввод закончился
     */
    public String readLine(String prompt) {
        if (interactive && prompt != null && !prompt.isEmpty()) {
            System.out.print(prompt);
        }
        if (!currentScanner.hasNextLine()) {
            return null;
        }
        return currentScanner.nextLine();
    }

    /**
     * Считывает строку без приглашения.
     *
     * @return считанная строка или null, если ввод закончился
     */
    public String readLine() {
        return readLine(null);
    }
}
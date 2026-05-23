package server;

import common.network.Request;
import common.network.Response;
import common.network.Serializer;
import server.collection.CollectionManager;
import server.services.FileManager;
import server.services.ServerLogger;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class UdpServer {

    private static final int BUFFER_SIZE = 65_507;
    private final DatagramChannel channel;
    private final Selector selector;
    private final Serializer serializer = new Serializer();
    private final CommandProcessor commandProcessor;
    private boolean running = true;
    private final FileManager fileManager;
    private final CollectionManager collectionManager;
    private static final Logger logger = ServerLogger.getLogger();

    public UdpServer(int port, CommandProcessor commandProcessor, FileManager fileManager, CollectionManager collectionManager) throws Exception {
        this.commandProcessor = commandProcessor;
        this.fileManager = fileManager;
        this.collectionManager = collectionManager;
        selector = Selector.open();
        channel = DatagramChannel.open();
        channel.bind(new InetSocketAddress(port));
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
    }

    public void start() throws Exception {
    ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
    BufferedReader serverConsole = new BufferedReader(new InputStreamReader(System.in));

    System.out.println("Сервер запущен. Доступные серверные команды: save, exit");
    logger.info("UDP сервер запущен на порту 5555");

    while (running) {
        selector.select(500);
        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            iterator.remove();

            if (!key.isValid()) {
                continue;
            }

            if (key.isReadable()) {
                buffer.clear();

                DatagramChannel datagramChannel = (DatagramChannel) key.channel();
                SocketAddress clientAddress = datagramChannel.receive(buffer);

                if (clientAddress == null) {
                    continue;
                }
                logger.info("Получен запрос от " + clientAddress);

                buffer.flip();

                byte[] data = new byte[buffer.remaining()];
                buffer.get(data);

                try {
                    Request request = (Request) serializer.deserialize(data);
                    logger.info("Получена команда: " + request.commandType());
                    Response response = commandProcessor.process(request);
                    sendResponse(response, clientAddress);
                    logger.info("Ответ отправлен клиенту " + clientAddress);
                } catch (Exception e) {
                    Response errorResponse = new Response(false, "Ошибка сервера: " + e.getMessage(), false, null);
                    logger.severe("Ошибка сервера: " + e.getMessage());
                    sendResponse(errorResponse, clientAddress);
                }
            }
        }
        if (serverConsole.ready()) {
            String command = serverConsole.readLine().trim();
            switch (command) {
                case "save" -> {
                    fileManager.save(collectionManager.getMap());
                    System.out.println("Коллекция сохранена");
                }
                case "exit" -> {
                    fileManager.save(collectionManager.getMap());
                    System.out.println("Коллекция сохранена. Сервер завершает работу");
                    running = false;
                }
                case "" -> {}
                default -> System.out.println("Неизвестная серверная команда. Доступно: save, exit");
            }
        }
    }
        channel.close();
        selector.close();
    }

    private void sendResponse(Response response, SocketAddress clientAddress) throws Exception {
        byte[] responseBytes = serializer.serialize(response);
        if (responseBytes.length > BUFFER_SIZE) {
            response = new Response(false, "Ответ слишком большой для UDP",false, null);
            responseBytes = serializer.serialize(response);
        }
        ByteBuffer outBuffer = ByteBuffer.wrap(responseBytes);
        channel.send(outBuffer, clientAddress);
    }
}
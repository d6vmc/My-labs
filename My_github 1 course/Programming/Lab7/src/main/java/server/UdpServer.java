package server;

import common.network.Request;
import common.network.Response;
import common.network.Serializer;
import server.collection.CollectionManager;
import server.services.ServerLogger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;

public class UdpServer {

    private static final int BUFFER_SIZE = 65_507;

    private final ForkJoinPool readingPool = new ForkJoinPool();
    private final ExecutorService processingPool = Executors.newCachedThreadPool();
    private final ExecutorService sendingPool = Executors.newFixedThreadPool(4);

    private final DatagramChannel channel;
    private final Selector selector;
    private final Serializer serializer = new Serializer();
    private final CommandProcessor commandProcessor;
    private final CollectionManager collectionManager;

    private boolean running = true;

    private static final Logger logger = ServerLogger.getLogger();

    public UdpServer(int port, CommandProcessor commandProcessor, CollectionManager collectionManager) throws Exception {
        this.commandProcessor = commandProcessor;
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
        System.out.println("Сервер запущен. Доступные серверные команды: exit");
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
                    readingPool.execute(() -> {
                        try {
                            Request request = (Request) serializer.deserialize(data);
                            logger.info("Получена команда: " + request.commandType());
                            processingPool.submit(() -> {
                                Response response;
                                try {
                                    response = commandProcessor.process(request);
                                } catch (Exception e) {
                                    response = new Response(
                                            false,
                                            "Ошибка обработки запроса: " + e.getMessage(),
                                            false,
                                            null
                                    );
                                }
                                Response finalResponse = response;
                                sendingPool.submit(() -> {
                                    try {
                                        sendResponse(finalResponse, clientAddress);
                                        logger.info("Ответ отправлен клиенту " + clientAddress);
                                    } catch (Exception e) {
                                        logger.severe("Ошибка отправки ответа: " + e.getMessage());
                                    }
                                });
                            });
                        } catch (Exception e) {
                            Response errorResponse = new Response(
                                    false,
                                    "Ошибка чтения запроса: " + e.getMessage(),
                                    false,
                                    null
                            );
                            sendingPool.submit(() -> {
                                try {
                                    sendResponse(errorResponse, clientAddress);
                                } catch (Exception ex) {
                                    logger.severe("Ошибка отправки ошибки клиенту: " + ex.getMessage());
                                }
                            });
                        }
                    });
                }
            }
            if (serverConsole.ready()) {
                String command = serverConsole.readLine().trim();

                switch (command) {
                    case "exit" -> {
                        System.out.println("Сервер завершает работу");
                        running = false;
                    }
                    case "save" -> {
                        System.out.println("Сохранение в файл отключено: коллекция хранится в PostgreSQL");
                    }
                    case "" -> {
                    }
                    default -> System.out.println("Неизвестная серверная команда. Доступно: exit");
                }
            }
        }
        readingPool.shutdown();
        processingPool.shutdown();
        sendingPool.shutdown();
        channel.close();
        selector.close();
    }

    private synchronized void sendResponse(Response response, SocketAddress clientAddress) throws Exception {
        byte[] responseBytes = serializer.serialize(response);

        if (responseBytes.length > BUFFER_SIZE) {
            response = new Response(
                    false,
                    "Ответ слишком большой для UDP",
                    false,
                    null
            );
            responseBytes = serializer.serialize(response);
        }
        ByteBuffer outBuffer = ByteBuffer.wrap(responseBytes);
        channel.send(outBuffer, clientAddress);
    }
}
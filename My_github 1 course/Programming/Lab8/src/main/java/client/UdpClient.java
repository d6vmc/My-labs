package client;

import common.network.Request;
import common.network.Response;
import common.network.Serializer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UdpClient {
    private static final int BUFFER_SIZE = 65_507;
    private final DatagramChannel channel;
    private final InetSocketAddress serverAddress;
    private final Serializer serializer;

    public UdpClient(String host, int port, Serializer serializer) throws Exception {
        this.channel = DatagramChannel.open();
        this.channel.configureBlocking(false);
        this.serverAddress = new InetSocketAddress(host, port);
        this.serializer = serializer;
    }

    public Response sendRequest(Request request) throws Exception {
        byte[] requestBytes = serializer.serialize(request);
        if (requestBytes.length > BUFFER_SIZE) {
            return new Response(false, "Запрос слишком большой для UDP", false,null);
        }
        ByteBuffer outBuffer = ByteBuffer.wrap(requestBytes);
        channel.send(outBuffer, serverAddress);
        ByteBuffer inBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        long start = System.currentTimeMillis();
        long timeoutMs = 5000;
        while (System.currentTimeMillis() - start < timeoutMs) {
            inBuffer.clear();
            var address = channel.receive(inBuffer);
            if (address == null) {
                Thread.sleep(50);
                continue;
            }
            inBuffer.flip();
            byte[] data = new byte[inBuffer.remaining()];
            inBuffer.get(data);
            return (Response) serializer.deserialize(data);
        }
        return new Response(false, "Сервер временно недоступен", false, null);
    }
    public void close() throws Exception {
        channel.close();
    }
}
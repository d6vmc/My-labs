package common.network;

import java.io.*;

public class Serializer {
    public byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(object);
        return bos.toByteArray();
    }

    public Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream in = new ObjectInputStream(bis);
        return in.readObject();
    }
}

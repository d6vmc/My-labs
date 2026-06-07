package common.network;

import java.io.Serializable;

public record Response(
        boolean success,
        String message,
        boolean exit,
        Object data
) implements Serializable {}
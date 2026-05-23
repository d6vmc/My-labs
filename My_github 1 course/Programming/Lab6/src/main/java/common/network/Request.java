package common.network;

import common.CommandType;

import java.io.Serializable;

public record Request(
        CommandType commandType,
        Object argument
) implements Serializable{}

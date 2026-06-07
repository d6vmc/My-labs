package common.network;

import common.CommandType;

import java.io.Serializable;

public record Request(
        String login,
        String password,
        CommandType commandType,
        Object argument
) implements Serializable{}

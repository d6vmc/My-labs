package common;

import java.io.Serializable;

public record ArgumentWithKey(
        long key,
        PersonData data
) implements Serializable {}
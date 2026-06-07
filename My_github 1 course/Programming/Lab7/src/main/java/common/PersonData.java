package common;

import common.domain.Coordinates;
import common.domain.Country;
import common.domain.Location;

import java.io.Serializable;
import java.time.LocalDate;

public record PersonData(
    String name,
    Coordinates coordinates,
    double height,
    LocalDate birthday,
    String passportId,
    Country nationality,
    Location location
) implements Serializable {}
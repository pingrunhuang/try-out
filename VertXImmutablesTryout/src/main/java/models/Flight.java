package models;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Flight {
    abstract public int getNo();
    abstract public String getDeparture();
    abstract public String getDestination();
}

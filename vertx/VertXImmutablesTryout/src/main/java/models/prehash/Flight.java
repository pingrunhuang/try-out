package models.prehash;

import org.immutables.value.Value;

@Value.Immutable(prehash = true)
public abstract class Flight {

    public abstract int getNo();

    public abstract String getDeparture();

    public abstract String getDestination();
}

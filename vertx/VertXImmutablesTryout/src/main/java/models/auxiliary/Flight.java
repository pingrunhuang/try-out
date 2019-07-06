package models.auxiliary;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Flight {

    /**
     * protected area
     * */
    abstract int getNo();

    /**
     * property that will be ignored by equals, hashCode and toString
     * */
    @Value.Auxiliary
    public abstract String getDeparture();

    @Value.Auxiliary
    public abstract String getDestination();
}

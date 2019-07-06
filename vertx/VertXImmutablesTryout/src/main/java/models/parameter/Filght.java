package models.parameter;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Filght {

    @Value.Parameter
    public abstract String getDeparture();

    @Value.Parameter
    public abstract String getDestination();

}

package models._default;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Flight {

    abstract public int getNo();

    abstract public String getDeparture();

    abstract public String getDestination();

    /**
     * Default value to be returned
     * */
    @Value.Default
    public Integer getCapacity(){
        return 200;
    }

}

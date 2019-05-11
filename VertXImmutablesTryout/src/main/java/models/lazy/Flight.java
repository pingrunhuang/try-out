package models.lazy;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Flight {

    public abstract int getNo();

    public abstract String getDeparture();

    public abstract String getDestination();

    /**
     * 1. Lazy attribute is auxiliary
     * 2. only compute once (similar to scala)
     * */
    @Value.Lazy
    public String toString(){
        return this.getDeparture() + "->" + this.getDestination();
    }

}

package models.auxiliary;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Generated;

/**
 * Immutable implementation of {@link Flight}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableFlight.builder()}.
 */
@SuppressWarnings("all")
@Generated({"Immutables.generator", "Flight"})
public final class ImmutableFlight extends Flight {
  private final int no;
  private final String departure;
  private final String destination;

  private ImmutableFlight(int no, String departure, String destination) {
    this.no = no;
    this.departure = departure;
    this.destination = destination;
  }

  /**
   * protected area
   * 
   */
  @Override
  int getNo() {
    return no;
  }

  /**
   * property that will be ignored by equals, hashCode and toString
   * 
   */
  @Override
  public String getDeparture() {
    return departure;
  }

  /**
   * @return The value of the {@code destination} attribute
   */
  @Override
  public String getDestination() {
    return destination;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Flight#getNo() no} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param no A new value for no
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableFlight withNo(int no) {
    if (this.no == no) return this;
    return new ImmutableFlight(no, this.departure, this.destination);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Flight#getDeparture() departure} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param departure A new value for departure
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableFlight withDeparture(String departure) {
    if (this.departure.equals(departure)) return this;
    String newValue = Preconditions.checkNotNull(departure, "departure");
    return new ImmutableFlight(this.no, newValue, this.destination);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Flight#getDestination() destination} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param destination A new value for destination
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableFlight withDestination(String destination) {
    if (this.destination.equals(destination)) return this;
    String newValue = Preconditions.checkNotNull(destination, "destination");
    return new ImmutableFlight(this.no, this.departure, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableFlight} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableFlight
        && equalTo((ImmutableFlight) another);
  }

  private boolean equalTo(ImmutableFlight another) {
    return no == another.no;
  }

  /**
   * Computes a hash code from attributes: {@code no}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 31;
    h = h * 17 + no;
    return h;
  }

  /**
   * Prints the immutable value {@code Flight} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("Flight")
        .omitNullValues()
        .add("no", no)
        .toString();
  }

  /**
   * Creates an immutable copy of a {@link Flight} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable Flight instance
   */
  public static ImmutableFlight copyOf(Flight instance) {
    if (instance instanceof ImmutableFlight) {
      return (ImmutableFlight) instance;
    }
    return ImmutableFlight.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableFlight ImmutableFlight}.
   * @return A new ImmutableFlight builder
   */
  public static ImmutableFlight.Builder builder() {
    return new ImmutableFlight.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableFlight ImmutableFlight}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  public static final class Builder {
    private static final long INIT_BIT_NO = 0x1L;
    private static final long INIT_BIT_DEPARTURE = 0x2L;
    private static final long INIT_BIT_DESTINATION = 0x4L;
    private long initBits = 0x7L;

    private int no;
    private String departure;
    private String destination;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code Flight} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(Flight instance) {
      Preconditions.checkNotNull(instance, "instance");
      no(instance.getNo());
      departure(instance.getDeparture());
      destination(instance.getDestination());
      return this;
    }

    /**
     * Initializes the value for the {@link Flight#getNo() no} attribute.
     * @param no The value for no 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder no(int no) {
      this.no = no;
      initBits &= ~INIT_BIT_NO;
      return this;
    }

    /**
     * Initializes the value for the {@link Flight#getDeparture() departure} attribute.
     * @param departure The value for departure 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder departure(String departure) {
      this.departure = Preconditions.checkNotNull(departure, "departure");
      initBits &= ~INIT_BIT_DEPARTURE;
      return this;
    }

    /**
     * Initializes the value for the {@link Flight#getDestination() destination} attribute.
     * @param destination The value for destination 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder destination(String destination) {
      this.destination = Preconditions.checkNotNull(destination, "destination");
      initBits &= ~INIT_BIT_DESTINATION;
      return this;
    }

    /**
     * Builds a new {@link ImmutableFlight ImmutableFlight}.
     * @return An immutable instance of Flight
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableFlight build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableFlight(no, departure, destination);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = Lists.newArrayList();
      if ((initBits & INIT_BIT_NO) != 0) attributes.add("no");
      if ((initBits & INIT_BIT_DEPARTURE) != 0) attributes.add("departure");
      if ((initBits & INIT_BIT_DESTINATION) != 0) attributes.add("destination");
      return "Cannot build Flight, some of required attributes are not set " + attributes;
    }
  }
}

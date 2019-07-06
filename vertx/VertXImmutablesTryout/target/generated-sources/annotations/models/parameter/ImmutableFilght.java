package models.parameter;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Generated;

/**
 * Immutable implementation of {@link Filght}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableFilght.builder()}.
 * Use the static factory method to create immutable instances:
 * {@code ImmutableFilght.of()}.
 */
@SuppressWarnings("all")
@Generated({"Immutables.generator", "Filght"})
public final class ImmutableFilght extends Filght {
  private final String departure;
  private final String destination;

  private ImmutableFilght(String departure, String destination) {
    this.departure = Preconditions.checkNotNull(departure, "departure");
    this.destination = Preconditions.checkNotNull(destination, "destination");
  }

  private ImmutableFilght(ImmutableFilght original, String departure, String destination) {
    this.departure = departure;
    this.destination = destination;
  }

  /**
   * @return The value of the {@code departure} attribute
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
   * Copy the current immutable object by setting a value for the {@link Filght#getDeparture() departure} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param departure A new value for departure
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableFilght withDeparture(String departure) {
    if (this.departure.equals(departure)) return this;
    String newValue = Preconditions.checkNotNull(departure, "departure");
    return new ImmutableFilght(this, newValue, this.destination);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Filght#getDestination() destination} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param destination A new value for destination
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableFilght withDestination(String destination) {
    if (this.destination.equals(destination)) return this;
    String newValue = Preconditions.checkNotNull(destination, "destination");
    return new ImmutableFilght(this, this.departure, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableFilght} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableFilght
        && equalTo((ImmutableFilght) another);
  }

  private boolean equalTo(ImmutableFilght another) {
    return departure.equals(another.departure)
        && destination.equals(another.destination);
  }

  /**
   * Computes a hash code from attributes: {@code departure}, {@code destination}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 31;
    h = h * 17 + departure.hashCode();
    h = h * 17 + destination.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code Filght} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("Filght")
        .omitNullValues()
        .add("departure", departure)
        .add("destination", destination)
        .toString();
  }

  /**
   * Construct a new immutable {@code Filght} instance.
   * @param departure The value for the {@code departure} attribute
   * @param destination The value for the {@code destination} attribute
   * @return An immutable Filght instance
   */
  public static ImmutableFilght of(String departure, String destination) {
    return new ImmutableFilght(departure, destination);
  }

  /**
   * Creates an immutable copy of a {@link Filght} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable Filght instance
   */
  public static ImmutableFilght copyOf(Filght instance) {
    if (instance instanceof ImmutableFilght) {
      return (ImmutableFilght) instance;
    }
    return ImmutableFilght.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableFilght ImmutableFilght}.
   * @return A new ImmutableFilght builder
   */
  public static ImmutableFilght.Builder builder() {
    return new ImmutableFilght.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableFilght ImmutableFilght}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  public static final class Builder {
    private static final long INIT_BIT_DEPARTURE = 0x1L;
    private static final long INIT_BIT_DESTINATION = 0x2L;
    private long initBits = 0x3L;

    private String departure;
    private String destination;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code Filght} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(Filght instance) {
      Preconditions.checkNotNull(instance, "instance");
      departure(instance.getDeparture());
      destination(instance.getDestination());
      return this;
    }

    /**
     * Initializes the value for the {@link Filght#getDeparture() departure} attribute.
     * @param departure The value for departure 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder departure(String departure) {
      this.departure = Preconditions.checkNotNull(departure, "departure");
      initBits &= ~INIT_BIT_DEPARTURE;
      return this;
    }

    /**
     * Initializes the value for the {@link Filght#getDestination() destination} attribute.
     * @param destination The value for destination 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder destination(String destination) {
      this.destination = Preconditions.checkNotNull(destination, "destination");
      initBits &= ~INIT_BIT_DESTINATION;
      return this;
    }

    /**
     * Builds a new {@link ImmutableFilght ImmutableFilght}.
     * @return An immutable instance of Filght
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableFilght build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableFilght(null, departure, destination);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = Lists.newArrayList();
      if ((initBits & INIT_BIT_DEPARTURE) != 0) attributes.add("departure");
      if ((initBits & INIT_BIT_DESTINATION) != 0) attributes.add("destination");
      return "Cannot build Filght, some of required attributes are not set " + attributes;
    }
  }
}

package _default;

import models._default.ImmutableFlight;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class DefaultUnitTest {
    @Test
    public void getDefaultCapacityTest(){
        ImmutableFlight boeing = ImmutableFlight.builder()
                .no(737)
                .departure("Beijing")
                .destination("Bali")
                .build();

        Assertions.assertThat(boeing.getCapacity()).isEqualTo(200);

        ImmutableFlight largerBoeing = boeing.withCapacity(1000);

        Assertions.assertThat(largerBoeing.getCapacity()).isEqualTo(1000);
    }
}

package auxiliary;

import models.auxiliary.ImmutableFlight;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class AuxiliaryUnitTest {

    @Test
    public void auxiliaryEqualTest(){
        ImmutableFlight boeing = ImmutableFlight.builder()
                .departure("Beijing")
                .destination("HK")
                .no(737)
                .build();
        ImmutableFlight modifiedBoeing = boeing.withDestination("Bali");

        /*
          meaning every attribute that is declared Auxiliary won't affect equals, toString and hashCode method
          */
        Assertions.assertThat(boeing.equals(modifiedBoeing)).isTrue();

        Assertions.assertThat(boeing.toString()).isEqualTo(modifiedBoeing.toString());

        Assertions.assertThat(boeing.hashCode()).isEqualTo(modifiedBoeing.hashCode());
    }
}

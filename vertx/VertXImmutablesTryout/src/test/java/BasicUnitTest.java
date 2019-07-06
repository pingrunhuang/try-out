import models.ImmutableFlight;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BasicUnitTest {
    @Test
    public void createAndModify(){
        ImmutableFlight boen = ImmutableFlight.builder()
                .no(737)
                .departure("Beijing")
                .destination("Bali").build();

        ImmutableFlight boen2 = boen
                .withDeparture("Bali")
                .withDestination("Beijing");

        assertThat(boen).isNotSameAs(boen2);

        assertThat(boen.getNo()).isEqualTo(737);

    }
}

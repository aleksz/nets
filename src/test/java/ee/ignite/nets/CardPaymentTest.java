package ee.ignite.nets;


import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class CardPaymentTest {

	@Test
	public void randomIdGeneration() {
		for (int i = 0; i < 10000; i++) {
			CardPayment cardPayment = new CardPayment(5, "eur", "et", "uid234", "localhost", "004");
			assertThat(cardPayment.getId())
				.isGreaterThan(201303100000l)
				.isLessThan(202312999999l);
		}
	}

}

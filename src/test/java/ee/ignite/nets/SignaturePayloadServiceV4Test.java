package ee.ignite.nets;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class SignaturePayloadServiceV4Test {

	private SignaturePayloadService service = new SignaturePayloadServiceV4();

	@Test
	public void generatePayloadForPayment() {
		String payload = service.generatePayload(new CardPayment(123.23, "EEK",
				"et", "uid1", "http://", "004"));

		assertThat(payload)
			.as("can not be empty").isNotEmpty()
			.as("length is always 183 chars").hasSize(183)
			.as("contains version").contains("004")
			.as("contains fixed length merchant id").contains("uid1      ")
			.as("contains zero padded amount in cents").contains("000000012323")
			.as("contains currency").contains("EEK")
			.as("contains fixed length url").contains("http://                 " +
					"                                                          " +
					"                                              ");
	}

	@Test
	public void generatePayloadForReceipt() {
		CardReceipt receipt = new CardReceipt(221423412, 4, "uid2", 4523,
				32543, "EEK", 52, "20130303095724", "Rladf Üsdf", "success");

		String payload = service.generatePayload(receipt);

		assertThat(payload)
			.as("can not be empty").isNotEmpty()
			.as("length is always 143 chars").hasSize(143)
			.as("contains version").contains("004")
			.as("contains fixed length merchant id").contains("uid2      ")
			.as("contains zero padded ecuno").contains("000221423412")
			.as("contains zero padded receipt no").contains("004523")
			.as("contains zero padded amount in cents").contains("000000032543")
			.as("contains currency").contains("EEK")
			.as("contains resp code").contains("052")
			.as("contains timestamp").contains("20130303095724")
			.as("contains fixed length msgdata").contains("Rladf Üsdf                              ")
			.as("contains fixed length action text").contains("success                                 ");
	}
}

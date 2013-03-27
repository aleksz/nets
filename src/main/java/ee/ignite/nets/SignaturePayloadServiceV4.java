package ee.ignite.nets;

import static java.lang.String.format;

public class SignaturePayloadServiceV4 extends SignaturePayloadService {

	@Override
	protected String generatePayloadWithoutLogging(CardPayment payment) {
		return super.generatePayloadWithoutLogging(payment)
				+ format("%-128s", payment.getCallbackUrl())
				+ "S";
	}
}

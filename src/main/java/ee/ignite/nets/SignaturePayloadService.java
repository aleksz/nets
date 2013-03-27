package ee.ignite.nets;

import static java.lang.String.format;

public class SignaturePayloadService {

	public String generatePayload(CardPayment payment) {
		return generatePayloadWithoutLogging(payment);
	}

	protected String generatePayloadWithoutLogging(CardPayment payment) {
		return payment.getVersion()
				+ format("%-10s", payment.getMerchantId())
				+ format("%-12s", payment.getId())
				+ format("%012d", payment.getAmountInCents())
				+ payment.getCurrency()
				+ payment.getDatetime();
	}

	public String generatePayload(CardReceipt receipt) {
		String result = format("%03d", receipt.getVersion())
				+ format("%-10s", receipt.getMerchantId())
				+ format("%012d", receipt.getId())
				+ format("%06d", receipt.getReceiptNo())
				+ format("%012d", receipt.getAmount())
				+ receipt.getCurrency()
				+ format("%03d", receipt.getResponseCode())
				+ receipt.getTimestamp()
				+ format("%-40s", receipt.getResponseMessage())
				+ format("%-40s", receipt.getResponseActionText());

		return result;
	}
}

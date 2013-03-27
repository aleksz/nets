package ee.ignite.nets;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CardPayment {

	private long id;
	private String merchantId;
	private String action = "gaf";
	private String version;
	private double amount;
	private String currency;
	private String datetime;
	private String language;
	private String callbackUrl;

	public CardPayment(double amount, String currency, String language,
			String merchantId, String callbackUrl, String version) {
		this.id = generateId();
		this.amount = amount;
		this.currency = currency;
		this.merchantId = merchantId;
		this.callbackUrl = callbackUrl;
		this.version = version;
		this.datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		this.language = language;
	}

	private static long generateId() {
		int random = 100000 + (int) (Math.random() * ((999999 - 100000) + 1));
		return new Long(new SimpleDateFormat("yyyyMM").format(new Date()) + random);
	}

	public long getAmountInCents() {
		return (long) (amount * 100);
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}
	
	public long getId() {
		return id;
	}
	
	public String getMerchantId() {
		return merchantId;
	}
	
	public String getAction() {
		return action;
	}
	
	public String getVersion() {
		return version;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public String getDatetime() {
		return datetime;
	}
	
	public String getLanguage() {
		return language;
	}
}

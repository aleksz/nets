package ee.ignite.nets;



public class CardReceipt {

	private long id;
	private int version;
	private String merchantId;
	private int receiptNo;
	private long amount;
	private String currency;
	private int responseCode;
	private String timestamp;
	private String responseMessage;
	private String responseActionText;
	
	public CardReceipt(long id, int version, String merchantId,
			int receiptNo, long amount, String currency, int responseCode,
			String timestamp, String responseMessage, String responseActionText) {
		this.id = id;
		this.version = version;
		this.merchantId = merchantId;
		this.receiptNo = receiptNo;
		this.amount = amount;
		this.currency = currency;
		this.responseCode = responseCode;
		this.timestamp = timestamp;
		this.responseMessage = responseMessage;
		this.responseActionText = responseActionText;
	}
	
	CardReceipt() {}

	public int getReceiptNo() {
		return receiptNo;
	}
	
	public long getId() {
		return id;
	}
	
	public int getVersion() {
		return version;
	}
	
	public String getMerchantId() {
		return merchantId;
	}
	
	public long getAmount() {
		return amount;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public int getResponseCode() {
		return responseCode;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public String getResponseMessage() {
		return responseMessage;
	}
	
	public String getResponseActionText() {
		return responseActionText;
	}
}

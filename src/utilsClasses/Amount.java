package utilsClasses;

public class Amount {

	private String currency;
	private int amount;

	public Amount(String currency, int amount) {
		super();
		this.currency = currency;
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public int getAmount() {
		return amount;
	}

}

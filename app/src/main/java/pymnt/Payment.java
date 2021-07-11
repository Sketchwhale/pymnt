package pymnt;

public class Payment {
	User to;
	User from;
	Double amount;

	public Payment (User from, User to, Double amount) {
		this.from	= from;
		this.to 	= to;
		this.amount = amount;
	}

	public Double getAmount () {
		return amount;
	}

	public User getTo () {
		return to;
	}

	public User getFrom () {
		return from;
	}

	public String toString() {
		return from + " must pay " + amount + " to " + to;

	}

}

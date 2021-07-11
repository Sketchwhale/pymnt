package pymnt;

public class Expense {
	User user;
	double amount;

	public Expense (User user, double amount){
		this.user 	= user;
		this.amount = amount;
	} 

	public User getUser () {
		return user;
	} 

	public double getAmount () {
		return amount;
	}
}

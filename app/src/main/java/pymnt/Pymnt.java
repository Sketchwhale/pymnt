package pymnt;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.EnumMap;

public class Pymnt {

	double totalSpent;
	double requiredAmount;
	List<Expense> expenses;
	Map<User, Double> user2expenditure;
	Map<User, Map<User, Double>> user2userAmounts = null;

	public Pymnt () {
		expenses = new ArrayList<>();

		user2expenditure = new EnumMap<>(User.class);
		user2expenditure.put(User.JOHN, 0.0);
		user2expenditure.put(User.MARY, 0.0);
		user2expenditure.put(User.PETER, 0.0);
		initUser2UserPayment();
		
	}

	public void initUser2UserPayment () {
		user2userAmounts = new EnumMap<>(User.class);
		Map<User, Double> johnToOther = new EnumMap<>(User.class);
		johnToOther.put(User.MARY, 0.0);
		johnToOther.put(User.PETER, 0.0);
		Map<User, Double> maryToOther = new EnumMap<>(User.class);
		maryToOther.put(User.JOHN, 0.0);
		maryToOther.put(User.PETER, 0.0);
		Map<User, Double> peterToOther = new EnumMap<>(User.class);
		peterToOther.put(User.JOHN, 0.0);
		peterToOther.put(User.MARY, 0.0);
		user2userAmounts.put(User.JOHN, johnToOther);
		user2userAmounts.put(User.MARY, maryToOther);
		user2userAmounts.put(User.PETER, peterToOther);
	}

    public static void main(String[] args) {
		Pymnt pymnt = new Pymnt();
    }

	public List<Expense> getExpenses () {
		return expenses;
	}
	
	public void addExpense (User user, double amount) {

		double expenditure = user2expenditure.get(user);
		user2expenditure.put(user, expenditure += amount);
		expenses.add(new Expense(user, amount));

		totalSpent 		= totalSpent + amount;
		requiredAmount 	= totalSpent/3;

	}

	public double getBalanceByUser (User user) {
		return user2expenditure.get(user) - requiredAmount;
	}

	public void sendPayment (User from, User to, Double amount) {
		Double current = user2userAmounts.get(from).get(to);
		user2userAmounts.get(from).put(to, current + amount);
	}

	public Map<User,Map<User, Double>> getUser2UserAmounts(User from, User to) {
		return user2userAmounts;
	}

	public List<Payment> getSettlementSolution () {

		List<Payment> solution = new ArrayList<>();

		double johnBalance = getBalanceByUser(User.JOHN);
		List<Payment> johnPayments = new ArrayList<>();
		if (johnBalance < 0) {
			double amount = (johnBalance * (- 1))/2;
			solution.add(new Payment(User.JOHN, User.MARY, amount));
			solution.add(new Payment(User.JOHN, User.PETER, amount));
		}

		double maryBalance = getBalanceByUser(User.MARY);
		List<Payment> maryPayments = new ArrayList<>();
		if (maryBalance < 0) {
			double amount = (maryBalance * (- 1))/2;
			solution.add(new Payment(User.MARY, User.JOHN, amount));
			solution.add(new Payment(User.MARY, User.PETER, amount));
		}

		double peterBalance = getBalanceByUser(User.PETER);
		List<Payment> peterPayments = new ArrayList<>();
		if (peterBalance < 0) {
			double amount = (peterBalance * (- 1))/2;
			solution.add(new Payment(User.PETER, User.MARY, amount));
			solution.add(new Payment(User.PETER, User.JOHN, amount));
		}

		return solution;

	}
}

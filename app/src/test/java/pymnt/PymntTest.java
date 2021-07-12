package pymnt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Map;

class PymntTest {

	@Test
	void canObtainEmptyListofExpenses() {
        Pymnt pymnt = new Pymnt();
		List<Expense> expenses = pymnt.getExpenses();
		assertTrue(expenses.isEmpty());
    }

	@Test
	void canAddExpense() {
        Pymnt pymnt = new Pymnt();
		pymnt.addExpense(User.JOHN, 100.0);
		List<Expense> expenses = pymnt.getExpenses();
		assertEquals(1, expenses.size());
    }

	@Test
	void canGetUserBalance() {
        Pymnt pymnt = new Pymnt();
		pymnt.addExpense(User.JOHN, 250.0);
		pymnt.addExpense(User.JOHN, 250.0);
		pymnt.addExpense(User.MARY, 100.0);
		double balance = pymnt.getBalanceByUser(User.PETER);
		assertEquals(-200, balance);
    }

	@Test
	void canSendPaymentFromUserToAnotherUser () {
        Pymnt pymnt = new Pymnt();
		pymnt.sendPayment(User.JOHN, User.MARY, 100.0);
		Map<User, Map<User, Double>> user2userAmounts =
			pymnt.getUser2UserAmounts(User.JOHN, User.MARY);
		assertEquals(user2userAmounts.get(User.JOHN).get(User.MARY), 100.0);
	}

	@Test
	void canGetSimpleSettlementSolution () {
        Pymnt pymnt = new Pymnt();
		pymnt.addExpense(User.MARY, 30.0);
		pymnt.addExpense(User.PETER, 30.0);
		double balance = pymnt.getBalanceByUser(User.JOHN);
		assertEquals(-20, balance);

		List<Payment> solution = pymnt.getSettlementSolution();
		assertEquals(solution.size(), 2);
		assertEquals(solution.get(0).getAmount(), 10.0);
		assertEquals(solution.get(1).getAmount(), 10.0);
	}

	@Test
	void canGetLessSimplePaymentSolution () {
        Pymnt pymnt = new Pymnt();
		pymnt.addExpense(User.MARY, 80.0);
		pymnt.addExpense(User.PETER, 10.0);
		assertEquals(-30, pymnt.getBalanceByUser(User.JOHN));
		assertEquals(50, pymnt.getBalanceByUser(User.MARY));
		assertEquals(-20, pymnt.getBalanceByUser(User.PETER));

		List<Payment> solution = pymnt.getSettlementSolution();
		assertEquals(solution.size(), 2);
		for (Payment p : solution) {
			System.out.println(p.toString());
			if (p.getFrom() == User.JOHN) {
				assertEquals(p.getTo(), User.MARY);
				assertEquals(p.getAmount(), 30.0);
			}
			if (p.getFrom() == User.PETER) {
				assertEquals(p.getTo(), User.MARY);
				assertEquals(p.getAmount(), 20.0);
			}
		}

	}
}

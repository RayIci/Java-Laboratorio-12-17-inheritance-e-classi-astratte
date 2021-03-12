package creditAccount;

public class HistoryCreditAccount extends CreditAccount implements HistoryAccount {
	private int history; // positive->deposit, negative->withdraw, zero->no operation

        // private auxiliary method to be used in undo() and redo() methods
	private int operation(int amount) {
		if (amount >= 0)
			return this.deposit(amount);
		return this.withdraw(-amount);
	}

        // check to be used in undo() and redo() methods
	protected int requireNonZeroHistory() {
		if (this.history == 0)
			throw new IllegalStateException("Operation history is empty");
		return this.history;
	}

	protected HistoryCreditAccount(int limit, int balance, Client owner) {
	    super(limit, balance, owner);
	    this.history = 0;
	}

	protected HistoryCreditAccount(int balance, Client owner) {
	    super(balance, owner);
	    this.history = 0;
	}

        // factory methods for the corresponding constructors
    
	public static HistoryCreditAccount newOfLimitBalance(int limit, int balance, Client owner) {
		return new HistoryCreditAccount(limit, balance, owner); 
	}

	public static HistoryCreditAccount newOfBalance(int balance, Client owner) {
		return new HistoryCreditAccount(balance, owner);
	}

        // public instance methods

	@Override
	public int deposit(int amount) {
	    super.deposit(amount);
	    this.history = amount;
	    return super.getBalance();
	}

	@Override
	public int withdraw(int amount) {
	    super.withdraw(amount);
	    this.history = -1 * amount;
	    return super.getBalance();
	}

	@Override
	public long undo() {
	    this.requireNonZeroHistory();
	    this.operation(history);
	    return super.getBalance();
	}

	@Override
	public long redo() {
		this.requireNonZeroHistory();
	    this.operation(history);
	    return super.getBalance();
	}
}

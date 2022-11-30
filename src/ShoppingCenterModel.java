

public class ShoppingCenterModel{

	private Queue<Customer> lineOne;
	private Queue<Customer> lineTwo;
	private Queue<Customer> expressLine;

	private AscendinglyOrderedList<Customer, String> customers;
	private AscendinglyOrderedList<Item, String> items;

	private int restockingLevel;
	private int checkOutStart;

	public ShoppingCenterModel(){
		lineOne = new Queue<>();
		lineTwo = new Queue<>();
		expressLine = new Queue<>();

		customers = new AscendinglyOrderedList<>();
		items = new AscendinglyOrderedList<>();

		restockingLevel = 0;
		checkOutStart = 0;
	}
}

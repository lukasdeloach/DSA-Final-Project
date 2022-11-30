

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

	public boolean itemSearch(Item item){
		String name = item.getName();
		boolean check = true;
		int result = items.search(name);
		if(result > -1){
			check = false;
		}
		return check;
	}

	public boolean customerSearch(Customer customer){
		String name = customer.getName();
		boolean check = true;
		int result = customers.search(name);
		if(result > -1){
			check = false;
		}
		return check;
	}
}

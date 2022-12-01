
import java.lang.*;

public class ShoppingCenterModel{

	private CheckOut<Customer> lineOne;
	private CheckOut<Customer> lineTwo;
	private CheckOut<Customer> expressLine;


	private AscendinglyOrderedList<Customer, String> customers;
	private AscendinglyOrderedList<Item, String> items;

	private int restockingLevel;
	// check out start will be 0-2, 0 being express, 1 being line 1, 2 being line 2
	private int checkOutStart;

	public ShoppingCenterModel(){
		lineOne = new CheckOut<>("first");
		lineTwo = new CheckOut<>("second");
		expressLine = new CheckOut<>("express");

		customers = new AscendinglyOrderedList<>();
		items = new AscendinglyOrderedList<>();

		restockingLevel = 0;
		checkOutStart = 0;
	}


	// temp until we create getItem and getCustomer
	public AscendinglyOrderedList getItems(){
		return items;
	}

	public AscendinglyOrderedList getCustomers(){
		return customers;
	}

	// next step, take in cusotmer name and create customer here
	public void addCustomer(Customer newCust){
		customers.add(newCust);
	}

	public void checkOut(){
		checkOutStart = (checkOutStart + 1) % 3;
		switch(checkOutStart){
			case 0:
				expressLine.dequeue();
				break;
			case 1:
				lineOne.dequeue();
				break;
			case 2:
				lineTwo.dequeue();
				break;
		}
	}

	public boolean itemSearch(String name){
		boolean check = true;
		int result = items.search(name);
		if(result > -1){
			check = false;
		}
		return check;
	}

	// method to validate customer status, if they return -1, the customer does not exist
	// if 0, customer is in checkout, if 1, customer is still shopping
	public int customerSearch(String name){
		int result = 0;
		int index = customers.search(name);
		if(index > -1){
			result = -1;
		}
		else{
			int trueIndex = index + customers.size();
			Customer cust = customers.get(trueIndex);
			if(cust.getCheckOut()){
				result = -2;
			}else{
				result = trueIndex;
			}
		}
		return result;
	}

	public String displayLine1(){
		return lineOne.toString();
	}

	public String displayLine2(){
                return lineTwo.toString();
        }
	
	public String displayExpress(){
		return expressLine.toString();
	}

	public String displayShoppers(){
		StringBuilder retStr = new StringBuilder();
		int numCust = customers.size();
		if(numCust == 0){
			retStr.append("No customers are in the Shopping Center!");
		}else{
			retStr.append("The following " + numCust + " customers are in the Shopping Center:\r");
			for(int i = 0; i < numCustomers; i++){
				Customer cust = customers.get(i);
				retStr.append("Customer " +cust.getName() +" with " + cust.getItemAmount() +...
						" items present for " + cust.getTotalTime() + " minutes.\n");
			}	
		}

		return retStr;
	}



}

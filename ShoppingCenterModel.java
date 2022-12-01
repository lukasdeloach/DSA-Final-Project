/*
 *
 * NEED TO ADDRESS HANDLING OF AMOUNT OF SHOPPERS ONLY 
 */

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

	// find out who is checking out next and return their name
	public String nextCheckOut(){
		checkOutStart = (checkOutStart + 1) % 3;
		String nextCust = null;
                switch(checkOutStart){
                        case 0:
                                nextCust = (expressLine.peek()).getName();
                                break;
                        case 1:
				nextCust = (lineOne.peek()).getName();
                                break;
                        case 2:
                                nextCust = (lineTwo.peek()).getName();
                                break;
                }
		return nextCust;
	}


	// check out person based on which checkout lane is next
	// take in param of if they leave or return shopping
	// if leave, remove from list of all customers aswell
	// returns int of how many items they have. If -1, they are leaving and can be ignored,
	// if they go back to shopping, returns the amount of items they have
	public int checkOut(boolean leave){
		int items = -1;
		checkOutStart = (checkOutStart + 1) % 3;
		Customer cust = null;
		switch(checkOutStart){
			case 0:
				cust = expressLine.dequeue();
				break;
			case 1:
				cust = lineOne.dequeue();
				break;
			case 2:
				cust = lineTwo.dequeue();
				break;
		}
		
		if(leave){
			int index = customers.search(cust.getName());
			index = index + customers.size();
			customers.remove(index);
		}else{
			items = cust.getItemAmount();	
		}
		return items;		
	}

	public int itemSearch(String name){
		int result = items.search(name);
		if(result > -1){
			result = -1;
		}
		return result;
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

	// Possible fix 1 vs multiple
	public String displayShoppers(){
		StringBuilder retStr = new StringBuilder();
		//TEMPORARY SOLUTION FOR HOW MANY CUSTOMERS ARE SHOPPING, NOT JUST ALL CUSTOMERS
		//CURRENTLY SUBTRACTION, POSSIBLE COUNTER
		int numCust = customers.size();
		int shoppers = numCust - lineOne.size() - lineTwo.size() - expressLine.size();
		if(shoppers == 0){
			retStr.append("No customers are in the Shopping Center!");
		}else{
			retStr.append("The following " + shoppers + " customers are in the Shopping Center:\r");
			for(int i = 0; i < numCust; i++){
				Customer cust = customers.get(i);
				if(!cust.getCheckOut()){
					retStr.append("Customer " +cust.getName() +" with " + cust.getItemAmount() +
						" items present for " + cust.getTotalTime() + " minutes.\n");
				}
			}	
		}

		return retStr.toString();
	}



}

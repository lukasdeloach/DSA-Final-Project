/**
 * Updated with driver options. No other features added.
 * - Lukas DeLoach
 */

import java.io.*;
import java.util.*;

public class Driver {

    static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    // create static reader to be used throughout driver, in accordance with coding guidlines
    // static as it belongs to class

    public static void main(String[] args) throws IOException {


        ShoppingCenterModel shoppingCenter = new ShoppingCenterModel();
	shoppingCenter = simulateStart(shoppingCenter);

        // Print out to user options for input
        System.out.println("Enter an option below:");
        System.out.println("0. Close the Shopping Center.\n1. Customer enter Shopping Center.\n2. Customer picks an item and places it in the shopping cart." +
                           "\n3. Customer removes an item from the shopping cart.\n4. Customer is done shopping.\n5. Customer checks out.\n6. Print info about customers who are shopping."+
                           "\n7. Print info about customers in checkout lines.\n8. Print info about items at or below re-stocking level.\n9. Reorder an item.");

        // do while that will repeat until user inputs a 0. Do while will run once the first time before initial input
        int choice = 0;
        do {
            boolean correct = false;
            while(!correct) { // until valid response given, will ask for input
                // print out each time to choose option from menu
                System.out.print("Make your menu selection now: ");
                // scan and read inputted option
                try {
                    choice = Integer.parseInt(stdin.readLine().trim());
                    System.out.println(choice);
                    correct = true;
                } catch(Exception e) {
                    invalidInput();
                }
            }

            // switch on inputed option. Provide options to method calls. If non-valid input, call method to display it is invalid.
            switch(choice) {
            case 0:
                exitProgram();
                break;
            case 1:
                inputCaseOne(shoppingCenter);
                break;
            case 2:
                inputCaseTwo(shoppingCenter);
                break;
            case 3:
                inputCaseThree(shoppingCenter);
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                inputCaseSix(shoppingCenter);
                break;
            case 7:
                inputCaseSeven(shoppingCenter);
                break;
	    case 8:
		inputCaseEight(shoppingCenter);
		break;
	    case 9:
		inputCaseNine(shoppingCenter);
		break;
            default:
                invalidInput();
                break;
            }
        } while(choice != 0);// do while choice is not 0


    }// end main

    // method to call when input is out of range
    public static void outOfRange() {
        System.out.println("Position specified is out of range!");
    }// end outOfRange call

    // method to declare when list is empty
    public static void listEmpty() {
        System.out.println("List is empty.");
    }// end listEmpty

    // method to exit program
    public static void exitProgram() {
        System.out.println("Exiting program...Good Bye");
    }// end exitProgram
    //
    public static ShoppingCenterModel simulateStart(ShoppingCenterModel store) throws IOException{

	    System.out.println("Welcome to the Shopping Center!!!");
	    System.out.println("\nPlease specify stock");
	    System.out.print(" How many items do you have? : ");
	    int numOfItems = convertToInt(stdin.readLine().trim());
	    System.out.println(numOfItems);
	    System.out.print("Please specify restocking amount: ");
	    int stockingAmount = convertToInt(stdin.readLine().trim());
	    store.setRestockingLevel(stockingAmount);
	    for(int i = 0; i < numOfItems; i++){
		    System.out.print(">>Enter item name : ");
		    String itemName = stdin.readLine().trim();
		    System.out.println(itemName);
		    System.out.print(">>How many " + itemName + "s? : ");
		    int itemNum = convertToInt(stdin.readLine().trim());
		    System.out.println(itemNum);
		    Item item = new Item(itemName, itemNum);
		    boolean status = store.addItem(item);
		    while(!status){
			    System.out.println("Invalid or Duplicate item. Try again");
			    System.out.print(">>Enter item name : ");
	                    itemName = stdin.readLine().trim();
        	            System.out.println(itemName);
                	    System.out.print(">>How many " + itemName + "s? : ");
                    	    itemNum = convertToInt(stdin.readLine().trim());
                    	    System.out.println(itemNum);
                    	    item = new Item(itemName, itemNum);
                    	    status = store.addItem(item);
		    }
	    }
	    System.out.print("Please select the checkout line that should check out customers first\n(regular1/regular2/express) : ");
            String checkOutLine = stdin.readLine().trim();
	    return store;
    }





    // method called when want to add item
    public static void inputCaseOne(ShoppingCenterModel store) throws IOException {
        // set up for variables needed
        int size = store.getCustomers().size();
        System.out.print(">>Enter customer name : ");
        String name = stdin.readLine().trim();
        System.out.println(name);
        if(store.addCustomer(name) == true) {
            System.out.println("Customer " + name + " is now in the Shopping Center.");
        }
        else {
            System.out.println("Customer " + name + " is already in the Shopping Center.");
        }
        // prompt for item to insert
    }// end method


    // method to add item based on index
    public static void inputCaseTwo(ShoppingCenterModel store) throws IOException {
        // prompt for and read index to remove from, if empty unecessary to call for prompt
        //
        System.out.println(">>Enter customer name : ");
        String name = stdin.readLine().trim();
        System.out.println(name);
        int custResult = store.customerSearch(name);
        while(custResult<0) {

            System.out.println(">>Enter customer name : ");
            name = stdin.readLine().trim();
            System.out.println(name);
            custResult = store.customerSearch(name);
        }
        Customer customer = (Customer) store.getCustomers().get(custResult);
        System.out.print("What item does " + name + " want? ");
        String itemName = stdin.readLine().trim();
        System.out.println(itemName);
        int itemResult = store.itemSearch(itemName);
        if(itemResult==-1) {
            System.out.println("No " + itemName + " in Shopping Center.");
        }

        else {
            itemResult += store.getItems().size();
            Item item = (Item) store.getItems().get(itemResult);
            if(item.getAmount() < 1) {
                System.out.println("No " + itemName + " in stock.");
            }
            else {
                store.customerAddItem(customer, item);;
                System.out.println("Customer " + name + " has " + customer.getItemAmount() + " in the shopping cart.");
            }
        }
    }// end inputCaseTwo
    //

    public static void inputCaseThree(ShoppingCenterModel store) throws IOException {
        if(store.getCustomers().isEmpty()) {
            System.out.println("No one is in the Shopping Center!");
        }
        else {
            System.out.print(">>Enter customer name : ");
            String name = stdin.readLine().trim();
            System.out.println(name);
            int custResult = store.customerSearch(name);
            while(custResult<0) {

                System.out.println(">>Enter customer name : ");
                name = stdin.readLine().trim();
                System.out.println(name);
                custResult = store.customerSearch(name);
            }
            Customer customer = (Customer) store.getCustomers().get(custResult);
            if(customer.getItemAmount() == 0) {
                System.out.println("Customer " + name + " does not have any items in their shopping cart!");
            }
            else {
                store.customerRemoveItem(customer);
                System.out.println("Customer " + name + " has now " + customer.getItemAmount() + " items in the shopping cart.");
            }
        }
    }
    //
    public static void inputCaseSix(ShoppingCenterModel store) {
        System.out.println(store.displayShoppers());
    }

    public static void inputCaseSeven(ShoppingCenterModel store) {
        System.out.println(store.displayLine1() + store.displayLine2() + store.displayExpress());
    }

    public static void inputCaseEight(ShoppingCenterModel store){
	    System.out.println("Items at re-stocking level: ");
	    System.out.println(store.displayRestockingLevelItems());
    }

    public static void inputCaseNine(ShoppingCenterModel store) throws IOException{
	    System.out.print("Enter item name to be re-ordered : ");
	    String name = stdin.readLine().trim();
	    System.out.println(name);
	    System.out.print("Enter number of " + name + "s to be re-ordered : ");
	    int num = convertToInt(stdin.readLine().trim());
	    System.out.println(num);
	    Item item = store.orderItem(name, num);
	    if(item==null){
		   System.out.println("Invalid item");
	    }
	    else{
		   System.out.println("Stock has now " + item.getAmount() + " " + item.getName() + "s.");
	    }
    }


    // method to declare that inputted option is not valid from menu items
    public static void invalidInput() {
        System.out.println("This is not an option. Try again.");
    }// end invalidInput


    /**
     * Method to convert to an integer from a string
     * @param s
     * @return i
     */
    private static int convertToInt(String s) {
        int i = -1;
        try {
            i = Integer.parseInt(s);
        }
        catch(Exception e) {
            System.out.println("Invalid number.");
        }
        return i;
    }

}// end class

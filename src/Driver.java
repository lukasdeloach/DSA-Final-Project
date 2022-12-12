/**
 * This is the Driver class that is used to run the entire Shopping Center model project, and allow for user input.
 * This class uses all classes developed in folder, either directly on indirectly.
 * The only data field in the class is a reader to take in input, and the main method holds a ShoppingCenterModel data field.
 * @author:  Lukas DeLoach and Jessica Rodgers
 * @version: 2022.12.12
 */

import java.io.*;
import java.util.*;

public class Driver {

    static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    // create static reader to be used throughout driver, in accordance with coding guidlines
    // static as it belongs to class
	
    /**
     * Main method to run the driver. Has data fields of Shopping center. Calls simulate start to set up the shopping center.
     * Also displlays options to user, and then loops in allowing input options until 0 is selected to close program.
     * @param args -- String array which is taken in from initial call to run
     */
    public static void main(String[] args) throws IOException {


        ShoppingCenterModel shoppingCenter = new ShoppingCenterModel();
        shoppingCenter = simulateStart(shoppingCenter);

        // Print out to user options for input
        System.out.println("\nHere are the choices to select from: ");
        System.out.println("\t0. Close the Shopping Center.\n\t1. Customer enter Shopping Center.\n\t2. Customer picks an item and places it in the shopping cart." +
                           "\n\t3. Customer removes an item from the shopping cart.\n\t4. Customer is done shopping.\n\t5. Customer checks out.\n\t6. Print info about customers who are shopping."+
                           "\n\t7. Print info about customers in checkout lines.\n\t8. Print info about items at or below re-stocking level.\n\t9. Reorder an item.");
        System.out.print("Make your menu selection now: ");

        // do while that will repeat until user inputs a 0. Do while will run once the first time before initial input
        int choice = 0;
        do {
            boolean correct = false;
            while(!correct) { // until valid response given, will ask for input
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
                inputCaseFour(shoppingCenter);
                break;
            case 5:
                inputCaseFive(shoppingCenter);
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
            System.out.print("\nSelect an operation from the following menu: ");
        } while(choice != 0);// do while choice is not 0


    }// end main

    /**
     * Method to exit program when 0 is inputted and display output.
     */
    public static void exitProgram() {
        System.out.println("Exiting program...Good Bye");
    }// end exitProgram
    
    /**
     * Method to simulate the start of the Shopping center and take in initial input.
     * Initilaizes items, item stock, restocking level, and itinials checkout lane to checkout customers first.
     * @param store -- Shopping center to simulate the start for
     * @return store -- Shopping center to use in rest of simulation.
     */
    public static ShoppingCenterModel simulateStart(ShoppingCenterModel store) throws IOException {

        System.out.println("Welcome to the Shopping Center!!!");
        System.out.println("\nPlease specify stock");
        System.out.print(" How many items do you have? ");
        int numOfItems = convertToInt(stdin.readLine().trim());
        System.out.println(numOfItems);
        System.out.print("Please specify restocking amount: ");
        int stockingAmount = convertToInt(stdin.readLine().trim());
        System.out.println(stockingAmount);
        store.setRestockingLevel(stockingAmount);
        for(int i = 0; i < numOfItems; i++) {
            System.out.print(">>Enter item name : ");
            String itemName = stdin.readLine().trim();
            System.out.println(itemName);
            System.out.print(">>How many " + itemName + "s? ");
            int itemNum = convertToInt(stdin.readLine().trim());
            System.out.println(itemNum);
            boolean status = store.addItem(itemName, itemNum);
            System.out.println(itemNum + " items of " + itemName + " have been placed in stock.");
            while(!status) {
                System.out.println("Invalid or Duplicate item. Try again");
                System.out.print(">>Enter item name : ");
                itemName = stdin.readLine().trim();
                System.out.println(itemName);
                System.out.print(">>How many " + itemName + "s? ");
                itemNum = convertToInt(stdin.readLine().trim());
                System.out.println(itemNum);
                status = store.addItem(itemName, itemNum);
            }
        }
        System.out.print("Please select the checkout line that should check out customers first (regular1/regular2/express): ");
        String checkOutLine = stdin.readLine().trim();
        System.out.println(checkOutLine);
        return store;
    }

    /**
     * Handle the first case of input.
     * A Customer is added to the store. This takes in input on name and attempts to add to the store. If they already exist in the store they will not be added.
     * @param store -- ShoppingCenterModel to add customer too.
     * @exception ioException -- IoException thrown if improper input is given
     */
    public static void inputCaseOne(ShoppingCenterModel store) throws IOException {
        // set up for variables needed
        boolean added;
        do {
            System.out.print(">>Enter customer name : ");
            String name = stdin.readLine().trim();
            System.out.println(name);
            added = store.addCustomer(name);
            if(added == true) {
                System.out.println("Customer " + name + " is now in the Shopping Center.");
            }
            else {
                System.out.println("Customer " + name + " is already in the Shopping Center!");
            }
        } while(added == false);
        // prompt for item to insert
    }// end method

    /**
     * Method to handle input of case two, where a customer adds an item to their cart.
     * Method takes in input for customer name,and seraches for them in the store. If they dont exist in the store, or in a checkout line, nothing will be added to their cart, and displayed to user. 
     * Else, it will prompt for what item to add, search for the item name, and then add if it is a valid item and if in stock. Then will report new amount of items in customer cart. This also updates stock of item taken internally in store
     * @param store -- store to add a item to a customer in.
     * @exception ioException -- throws exception if improper input is given
     */
    public static void inputCaseTwo(ShoppingCenterModel store) throws IOException {
        // prompt for and read index to remove from, if empty unecessary to call for prompt
        if(!store.isEmpty()) {
            System.out.print(">>Enter customer name : ");
            String name = stdin.readLine().trim();
            System.out.println(name);
	    Customer cust = store.customerSearch(name);
            while(cust==null) {
                System.out.println("Customer " + name + " is not in shopping center!");
	        System.out.print(">>Enter customer name : ");
                name = stdin.readLine().trim();
                System.out.println(name);
                cust = store.customerSearch(name);
	    }
	    while(cust.getCheckOut()){
		System.out.println("Customer "+ name+ " is in a checkoutline !");
		System.out.print(">>Enter customer name : ");
                name = stdin.readLine().trim();
                System.out.println(name);
                cust = store.customerSearch(name);
	    }
		
            System.out.print("What item does " + name + " want? ");
            String itemName = stdin.readLine().trim();
            System.out.println(itemName);
            Item item = store.itemSearch(itemName);
            if(item == null) {
                System.out.println("No " + itemName + "s in Shopping Center.");
            }

            else{ 
                if(item.getAmount() < 1) {
                    System.out.println("No " + itemName + "s in stock.");
                }
                else {
                    store.customerAddItem(cust, item);
                    int custItems = cust.getItemAmount();
                    if(custItems != 1) {
                        System.out.println("Customer " + name + " has now " + cust.getItemAmount() + " items in the shopping cart.");
                    } else {
                        System.out.println("Customer " + name + " has now " + cust.getItemAmount() + " item in the shopping cart.");
                    }
		}
	    }
        } else {
            System.out.println("\tNo one is in the Shopping Center!");
        }
    }// end inputCaseTwo

    /**
     * This method handles input case three, where an item is to be removed from a customers cart.
     * This method asks for cusotmer name to remove item from cart. Validates if they exist and not in a checkout lane, and otherwise displays if so. 
     * Then, validates if the customer has anything in their cart, and displays if empty, and then if not, removes an item and displays now updated customer information.
     * @param store -- ShoppingCenterModel to update cusotmer for.
     * @exception ioException -- IoException thrown if improper input is given.
     */
    public static void inputCaseThree(ShoppingCenterModel store) throws IOException {
        if(store.isEmpty()) {
            System.out.println("\tNo one is in the Shopping Center!");
        }
        else {
            System.out.print(">>Enter customer name : ");
            String name = stdin.readLine().trim();
            System.out.println(name);
            Customer cust = store.customerSearch(name);
	    while(cust==null) {
                System.out.println("Customer " + name + " is not in shopping center!");
                System.out.print(">>Enter customer name : ");
                name = stdin.readLine().trim();
                System.out.println(name);
                cust = store.customerSearch(name);
            }
            while(cust.getCheckOut()){
                System.out.println("Customer "+ name+ " is in a checkoutline !");
                System.out.print(">>Enter customer name : ");
                name = stdin.readLine().trim();
                System.out.println(name);
                cust = store.customerSearch(name);
            }
            int amount = cust.getItemAmount();
            //fix to call item amount once
            if(amount == 0) {
                System.out.println("Customer " + name + " does not have any items in their shopping cart!");
            }
            //get rid of repeated code
            else {
                store.customerRemoveItem(cust);
                if(cust.getItemAmount() == 1) {
                    System.out.println("Customer " + name + " has now " + cust.getItemAmount() +
                                       " item in the shopping cart.");
                } else {
                    System.out.println("Customer " + name + " has now " + cust.getItemAmount() +
                                       " items in the shopping cart.");
                }
            }
        }
    }

    /**
     * This method puts the customer who has been in the store the longest in the proper checkout line. 
     * If customers are in the store, and shopping, it will use Shopping center methods to find the longest customer. If they have no items in their cart, they will be prompted if they should leave or return shopping. Otherwise, they will be put in the porper checkout line. The final decision and update will be displayed to user, and if in a line, all customer information, and what line they are in.
     * @param store -- ShoppingCenterModel that is to be updated.
     * @exception ioException -- IOException thrown with improper input
     */
    public static void inputCaseFour(ShoppingCenterModel store) throws IOException {
        if(store.isEmpty()) {
            System.out.println("\tNo customers in the Shopping Center!");
        }
        else {
            Customer cust = store.getLongestShopper();
            String name = cust.getName();
            int items = cust.getItemAmount();
            if(cust == null) {
                System.out.println("Every Customer is in a Checkout Line");
            }
            else if(cust.getItemAmount() == 0) {
                System.out.print("Should customer " + name + " leave or keep on shopping? Leave?(Y/N): ");
                String answer = stdin.readLine().trim();
                System.out.println(answer);
		String status = null;
                if(answer.equalsIgnoreCase("y")) {
                    status = store.doneShopping(true);
                }
                else {
                    status = store.doneShopping(false);
                }
		System.out.println(status);
            }
            else {
		System.out.println(store.doneShopping());
            }
        }
    }

    /**
     * This mehtod handles input case 5, of a person checking out of a checkout lane. 
     * If there are any customers in any checkout lane, it will confirm if they should checkout or go back ot shopping. Based on user confimration, it will display the proper infomration of the customer and if they stayed or left.
     * @param store -- ShoppingCenterModel to be updated.
     * @exception ioExcecption -- throws if improper input is given.
     */
    public static void inputCaseFive(ShoppingCenterModel store) throws IOException {
        String name = store.nextCheckOut();
        if(name == null) {
            System.out.println("\tNo customers in any line.");
        } else {
            boolean leave = true;
            String confirm;
            boolean correct = false;
            do {
                System.out.print("Should customer " +  name + " leave or keep on shopping? Leave?(Y/N): ");
                confirm = stdin.readLine().trim();
		System.out.println(confirm);
                if(confirm.equalsIgnoreCase("Y")) {
                    correct = true;
                } else if(confirm.equalsIgnoreCase("N")) {
                    leave = false;
                    correct = true;
                } else {
                    invalidInput();
                }
            } while(correct == false);

            Customer left = store.checkOut(leave);
            if(leave == true) {
                System.out.println("\nCustomer " + name + " is now leaving the Shopping Center.");
            } else {
                int items = left.getItemAmount();
                System.out.print("\nCustomer " + name + " with ");
                if(items > 1) {
                    System.out.print(items + " items ");
                } else {
                    System.out.print(items + " item ");
                }
                System.out.println("returned to shopping.");
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

    public static void inputCaseEight(ShoppingCenterModel store) {
        System.out.println("Items at re-stocking level: ");
        System.out.print(store.displayRestockingLevelItems());
    }
    // change how it is retrieved, need to confirm if it exists first, then
    // display if not existant, so search, return item, directly adjust, or pass to
    // method to update
    // CONFRIM BEFORE CORRECTION
    // -- AS OF NOW, SEARCHES TWICE
    public static void inputCaseNine(ShoppingCenterModel store) throws IOException {
        System.out.print("Enter item name to be re-ordered : ");
        String name = stdin.readLine().trim();
        System.out.println(name);
        Item item = store.itemSearch(name);
        if(item == null) {
            System.out.println(name + " is not it stock!");
        } else {
            // search goes here, confirm if it exists
            System.out.print("Enter number of " + name + "s to be re-ordered : ");
            int num = convertToInt(stdin.readLine().trim());
            System.out.println(num);
            store.orderItem(item, num);
            // then order based on name/item
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

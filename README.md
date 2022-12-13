## Getting Started

Welcome to the Shopping Center Model by Jess Rodgers and Lukas DeLoach - Fall 2022 Honor's Data Structure's and Algorithms under Dr. H 

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain the Java code

## Project Environment Rules and Guidelines

 - '0' : Please see Rationale.txt file for more info

- '1.' : When the program starts, the shopping environment is initialized by prompting the user to specify the number of available items and the restocking level for all items, then the program should prompt for the name and the stock quantities of these items in the shopping center. The restocking level will be the same for all items.

- '2.' :        The customers enter the Shopping Center (option 1). The names for the customers are unique identifiers.

- '3.' :        There are 3 checkout lines. One for express checkout (for customers with the number of items <=4) and 2 regular checkout lines. The names are express, regular1 and regular2.

- '4.':        The customers inside the Shopping Center can pick up items and place them in their shopping cart (option 2) or can remove items from their cart (option 3). When a customer picks up an item, the program should ask for the name of the item and the amount of stock for that item decreases by one (only if there is stock). For the customer, only the number of items in the cart matters, not the specific items. When a customer removes and item from the cart (option 3), the number of items in the shopping cart decreases, but the item that was removed does not go back into the stock (!!!). In order to simulate the passing of time, you should assume that every time a customer places an item (that is in stock) into or removes an item from the shopping cart, a minute passes (for everybody in the Shopping Center). At any moment, just one customer can perform such an operation.

- '5.':        The first customer who finishes shopping (option 4) is the one who has been in the Shopping Center the longest. When the customer finishes shopping the customer goes into one of the three checkout lines. If the customer has <=4 items, they can use the express checkout line. Otherwise the customer chooses the shortest regular line. If the express line is twice as long as a regular line a customer with <=4 items will choose the shortest regular line for checkout instead.

- '6.':      Customers who are at the front of the lines can check out and leave or can decide to return and shop some more (option 5). Customers check out in a fair manner: all three checkout lines take turns in checking out customers.  If there is no customer in the line whose turn it is to check out a customer, then the next checkout line that has customers in line will check out the first customer in line. If the customer decides to return to shopping, the time spent in the Shopping Center is reset.

- '7.':        The Shopping Center system should offer information about the customers who are shopping (option 6), about the customers who are waiting in the three checkout lines to leave the Shopping Center (option 7) and about the items in the shopping center that are at or below re-stocking level (option 8).

- '8.':        The Shopping Center system should offer the option to restock a specified item (option 9). The item name and restocking amount should be specified. This should allow restocking of items at any stock level, even though they are not at or below restocking level.

- '9."        If menu option 4 is chosen (Customer is done shopping) and the customer who has been in the Shopping Center the longest has no items in the cart, then the customer should be given the choice to leave or to return to shopping. The customer does not need to stand in line to check out. If the customer returns to shopping, the time spent in the Shopping Center will be reset.

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

## To combile
- '1': Get all java files into working folder
- '2': Compile with javac *.java
- '3': Run with java Driver

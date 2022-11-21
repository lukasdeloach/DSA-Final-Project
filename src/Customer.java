
/**
 * Customer class
 * Will add Javadoc later
 * @author Lukas DeLoach
 */
public class Customer {

    private String name;
    private Stack<Item> shoppingCart;
    private int totalTime;
    private int itemAmount;

    public Customer(String name){

        if(!name.isEmpty()){
            this.name = name;
        }
        shoppingCart = new Stack<Item>();
        itemAmount = 0;
        totalTime = 0;
    }

    /**
     * Basic method will add more logic later
     * */
    public void addItem(Item item){
	    shoppingCart.push(item);
	    itemAmount++;
    }

    /**
     * Basic method will add more logic later
     * */
    public Item removeItem(){
	    Item item = shoppingCart.pop();
	    return item;
    }	    

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Stack<Item> getShoppingCart(){
        return shoppingCart;
    }

    public void setShoppingCart(Stack<Item> shoppingCart){
        this.shoppingCart = shoppingCart;
    }

    public int getTotalTime(){
        return totalTime;
    }

    public void setTotalTime(int totalTime){
        this.totalTime = totalTime;
    }

    public int getItemAmount(){
        return itemAmount;
    }

    public void setItemAmount(int itemAmount){
        this.itemAmount = itemAmount;
    }
}

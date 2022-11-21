
/**
 * Customer class
 * Will add Javadoc later
 * @author Lukas DeLoach
 */
public class Customer {

    private String name;
    private ListArrayBasedPlusN<Item> shoppingCart;
    private int totalTime;
    private int itemAmount;

    public Customer(String name){

        if(!name.isEmpty()){
            this.name = name;
        }
        shoppingCart = new ListArrayBasedPlusN<Item>();
        itemAmount = shoppingCart.size();
        totalTime = 0;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public ListArrayBasedPlusN<Item> getShoppingCart(){
        return shoppingCart;
    }

    public void setShoppingCart(ListArrayBasedPlusN<Item> shoppingCart){
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

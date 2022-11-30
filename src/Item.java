/**
 * Item Class - will add javadoc later
 * @author Lukas DeLoach
 */
public class Item extends KeyedItem<String>{

    private String name;
    private int amount;

    public Item(String name, int amount){
        super(name); 
	this.name = name;
        this.amount = amount;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getAmount(){
        return amount;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }
}

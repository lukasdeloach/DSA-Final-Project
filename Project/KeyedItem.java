/**
 * KeyedItem class that extends Comparable. This class has a searchKey that is comparable, of generic type.
 */
public abstract class KeyedItem<KT extends Comparable<? super KT>> {
    private KT searchKey;

    /**
     * Constructor for KeyedItem class that has one parameter of generic type to set the value of the searchKey.
     * @param key -- Generic type to be used as comparable value and set serachKey variable.
     */
    public KeyedItem(KT key) {
        searchKey = key;
    }  // end constructor

    /**
     * Getter method of return type KT that returns the data field searchKey
     * @return searchKey - of type KT that holds the value of the key
     */
    public KT getKey() {
        return searchKey;
    } // end getKey
} // end KeyedItem


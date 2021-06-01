import components.set.Set;

/**
 * Model interface.
 *
 *
 *
 * @author MEEEEEEEEEEEEEEEEEEEEE im not commenting these, you get it
 */
public interface GetterModel {

    String displayFile();

    void setDisplayFile(String input);

    Set<String> serverSet();

    void setServerSet(Set<String> serverIn);

    Set<String> cartSet();

    void setCartSet(Set<String> cartIn);

    void addToCartSet(String adder);

    void removeFromCartSet(String remo);
}

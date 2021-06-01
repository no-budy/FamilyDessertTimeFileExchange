import components.set.Set;
import components.set.Set1L;

/**
 * Model interface.
 *
 *
 *
 * @author MEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
 *
 */
public final class GetterModel1 implements GetterModel {

    private String displayFile;
    private Set<String> serverSet, cartSet;

    public GetterModel1() {
        //init the stuff
        this.displayFile = "Choose a File to Upload!";
        this.serverSet = new Set1L<>();
        this.cartSet = new Set1L<>();

    }

    @Override
    public String displayFile() {
        return this.displayFile;
    }

    @Override
    public void setDisplayFile(String input) {
        this.displayFile = input;
    }

    @Override
    public Set<String> serverSet() {
        return this.serverSet;
    }

    @Override
    public void setServerSet(Set<String> serverIn) {
        this.serverSet.transferFrom(serverIn);
    }

    @Override
    public Set<String> cartSet() {
        return this.cartSet;
    }

    @Override
    public void setCartSet(Set<String> cartIn) {
        this.cartSet.transferFrom(cartIn);
    }

    @Override
    public void addToCartSet(String adder) {
        this.cartSet.add(adder);
    }

    @Override
    public void removeFromCartSet(String remo) {
        this.cartSet.remove(remo);
    }

}

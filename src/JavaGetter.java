/**
 * scp gui - that is all
 *
 * @author me
 *
 */
public final class JavaGetter {

    private JavaGetter() {
    }

    /**
     */
    public static void main(String[] args) {
        GetterModel model = new GetterModel1();
        GetterView view = new GetterView1();
        GetterController controller = new GetterController1(model, view);

        view.registerObserver(controller);
    }

}

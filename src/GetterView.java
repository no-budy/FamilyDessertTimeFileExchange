import java.awt.event.ActionListener;

import components.set.Set;

/**
 * View interface.
 *
 * @author Me
 *
 */
public interface GetterView extends ActionListener {

    /**
     */
    void registerObserver(GetterController controller);

    /**
     * Updates input display based on String provided as argument.
     *
     * new value of input display
     */
    void updateServerDisplay(Set<String> serverSet);

    /**
     * Updates output display based on String provided as argument.
     *
     * new value of output display
     */
    void updateCartDisplay(Set<String> cartSet);

    void updateFileDisplay(String fileDisplay);

}

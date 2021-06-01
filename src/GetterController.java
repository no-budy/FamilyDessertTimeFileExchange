/**
 * Controller interface.
 */
public interface GetterController {

    /**
     */
    void processRefreshEvent();

    //process the cart section
    void processToCart(String inputFile);

    //another cart function
    public void processRemoveCartItem(String inFile);

    //download what is in the cart
    void processDownload();

    //use the pop up window to update the display file to upload
    void processFileToUp();

    //will send the file from the display section
    void processFileSend();

}

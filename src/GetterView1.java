import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import components.map.Map;
import components.map.Map1L;
import components.set.Set;

/**
 * View interface.
 *
 * @author ME
 *
 */
@SuppressWarnings("serial")
public final class GetterView1 extends JFrame implements GetterView {

    private GetterController controller;

    private int ROWS_OF_SERVER = 1;
    private int ROWS_OF_CART = 1;

    //Create the Buttons that aren't files
    private final JButton refreshButton, toCartButton, downloadButton,
            fileUploadButton, sendButton;
    //2 button panels for the other things
    private JPanel serverPanel = new JPanel(
            new GridLayout(this.ROWS_OF_SERVER, 1));
    private JPanel cartPanel = new JPanel(new GridLayout(this.ROWS_OF_CART, 1));

    private JLabel label = new JLabel();

    //BUTTONS FILES ARRAY THING MAYBE MAP
    private JButton[] serverFiles = new JButton[20];
    private JButton[] cartFiles = new JButton[20];

    private Map<String, JButton> serverMap = new Map1L<String, JButton>();
    private Map<String, JButton> cartMap = new Map1L<String, JButton>();

    public GetterView1() {
        //Title the window
        super("Family Dessert File Exchange Center!");

        //Set up the GUI widgets

        //first buttons
        this.refreshButton = new JButton("Refresh");
        this.toCartButton = new JButton("To Cart");
        this.downloadButton = new JButton("Download");
        this.fileUploadButton = new JButton("Select File");
        this.sendButton = new JButton("Upload");

        //make those scrollable
        JScrollPane serverScroll = new JScrollPane(this.serverPanel);
        JScrollPane cartScroll = new JScrollPane(this.cartPanel);

        //Organize main window
        this.setLayout(new GridLayout(1, 2));

        //Create the panel for the lists of files and also the logo side
        JPanel serverAndCart = new JPanel(new GridLayout(2, 1));
        JPanel logoAndFile = new JPanel(new GridLayout(3, 1));

        JPanel filetext = new JPanel(new FlowLayout());
        filetext.add(this.label);

        //make image
        //ImageGetter m = new ImageGetter();
        //logoAndFile.add(m);
        logoAndFile.add(filetext);
        logoAndFile.add(this.fileUploadButton);
        logoAndFile.add(this.sendButton);

        //Create the split panel for server status and 2 buttons
        JPanel serverAndButtons = new JPanel(new GridLayout(2, 1));
        JPanel refreshAndCart = new JPanel(new FlowLayout());
        refreshAndCart.add(this.refreshButton);
        //refresh AndCart has nothing to do with the cart anymore IT IS JUST THE REFRESH BUTTONS

        //????????????????????????????????????
        //maybe add back for toCart buttons
        //refreshAndCart.add(this.toCartButton);

        //add the top server panel
        serverAndButtons.add(serverScroll);
        serverAndButtons.add(refreshAndCart);

        //finish the top
        serverAndCart.add(serverAndButtons);

        //NOW finish the bottom cart and download
        JPanel cartAndDown = new JPanel(new GridLayout(2, 1));
        cartAndDown.add(cartScroll);
        cartAndDown.add(this.downloadButton);

        //add to the bottom
        serverAndCart.add(cartAndDown);

        //Organize the window with the Panels
        this.add(serverAndCart);
        this.add(logoAndFile);

        //Observers ------------------------------------
        this.refreshButton.addActionListener(this);
        this.toCartButton.addActionListener(this);
        this.downloadButton.addActionListener(this);
        this.fileUploadButton.addActionListener(this);
        this.sendButton.addActionListener(this);

        //--------------------
        //--------------------
        //do the other button setup here
        //for the buttons that are the files
        //this.label.setText("Choose a File!");
        //dont need above

        //Start application Window(s)
        this.pack();
        this.setSize(1200, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    @Override
    public void registerObserver(GetterController controller) {
        this.controller = controller;
    }

    /**
     * Updates input display based on String provided as argument
     *
     */
    @Override
    public void updateServerDisplay(Set<String> serverSet) {

        this.ROWS_OF_SERVER = serverSet.size();
        int size = this.ROWS_OF_SERVER;

        //create a copy of the set
        Set<String> setCopy = serverSet.newInstance();
        setCopy.transferFrom(serverSet);

        //get ready to start over
        this.serverPanel.removeAll();
        //reset the layout
        this.serverPanel.setLayout(new GridLayout(this.ROWS_OF_SERVER, 1));

        for (int i = 0; i < size; i++) {
            //just to make sure the number of files is correct.
            //System.out.println("button - make" + i);

            //assign the string to a JButton
            String current = setCopy.removeAny();
            this.serverFiles[i] = new JButton(current);
            //register listener
            this.serverFiles[i].addActionListener(this);

            //Add to the panel
            this.serverPanel.add(this.serverFiles[i]);

            //add to the map for later
            this.serverMap.add(current, this.serverFiles[i]);
            //add back to the set
            serverSet.add(current);
        }

        this.serverPanel.repaint();
        this.serverPanel.validate();

    }

    /**
     * Updates output display based on String provided as argument
     */
    @Override
    public void updateCartDisplay(Set<String> cartSet) {

        this.ROWS_OF_CART = cartSet.size();
        int size = this.ROWS_OF_CART;
        Set<String> setCopy = cartSet.newInstance();
        setCopy.transferFrom(cartSet);

        //get ready to start over
        this.cartPanel.removeAll();

        this.cartPanel.setLayout(new GridLayout(this.ROWS_OF_CART, 1));

        //This for loop is the exact same as the one above
        for (int i = 0; i < size; i++) {
            String current1 = setCopy.removeAny();
            System.out.println("button - make" + i);
            this.cartFiles[i] = new JButton(current1);
            this.cartFiles[i].addActionListener(this);
            this.cartPanel.add(this.cartFiles[i]);
            this.cartMap.add(current1, this.cartFiles[i]);
            cartSet.add(current1);
        }

        this.cartPanel.repaint();
        this.cartPanel.validate();
        // this.repaint();
        // this.validate();
    }

    @Override
    public void updateFileDisplay(String fileDisplay) {
        this.label.setText(fileDisplay);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        //determine Event
        Object source = e.getSource();
        JButton sauce = (JButton) source;

        if (this.serverMap.hasValue(sauce) || this.cartMap.hasValue(sauce)) {
            //split the two up
            if (this.cartMap.hasValue(sauce)) {

                String key = this.cartMap.key(sauce);
                //The cart buttons should just remove and refresh the list of cart buttons
                //do cart buttons first????
                this.cartMap.remove(key);
                this.controller.processRemoveCartItem(key);
                this.controller.processRefreshEvent();

            } else {

                //DONT REMOVE from the server list
                //this should just add to the cart set
                String keyg = this.serverMap.key(sauce);

                System.out.println(keyg);

                this.serverMap.remove(keyg);
                this.controller.processToCart(keyg);
                this.controller.processRefreshEvent();
            }
        } else {
            //if the button was not a file buttons
            if (source == this.refreshButton) {
                this.controller.processRefreshEvent();
            } else if (source == this.sendButton) {
                // do this DOES THE SENDING
                this.controller.processFileSend();
                this.controller.processRefreshEvent();
            } else if (source == this.downloadButton) {
                this.controller.processDownload();
            } else if (source == this.toCartButton) {
                //TODO get the STRING of the FILE
                //THIs may become something only called by the other buttons
                //there will not but a to cart button
                // this.controller.processToCart();
            } else if (source == this.fileUploadButton) {
                //THIS JUST GET THE FILE CALLS THE BROWSER LIBRARY
                this.controller.processFileToUp();
            }
        }

        //Figure out how to find the file buttons

        this.setCursor(Cursor.getDefaultCursor());
    }

}

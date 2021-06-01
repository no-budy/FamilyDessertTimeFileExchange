import java.io.File;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import components.set.Set;
import components.set.Set1L;

/**
 * Controller class.
 *
 */
public final class GetterController1 implements GetterController {

    private JSch jssh = new JSch();
    private Session session;

    private String user = ""; //Here is where you input the user
    private String host = ""; //Here is where you input the domain or ip
    private int port = 22; //Enter your default port here
    private String Mailbox = ""; //Here is where you input the path the 'Mailbox' directory where you want the program to send and check for files
    private String Password = ""; //Enter the password here
    //TODO
    //KEYS

    //
    /**
     * Model object.
     */
    private final GetterModel model;

    /**
     * View object.
     */
    private final GetterView view;

    /**
     * Updates view to display model
     */
    private static void updateViewToMatchModel(GetterModel model,
            GetterView view) {
        /*
         * Get model info
         */
        String displayFile = model.displayFile();
        Set<String> serverSet = model.serverSet();
        Set<String> cartSet = model.cartSet();
        /*
         * Update view to reflect changes in model
         */
        //this works
        view.updateFileDisplay(displayFile);
        view.updateCartDisplay(cartSet);
        view.updateServerDisplay(serverSet);
    }

    /**
     * Constructor - connects to the model and view it coordinates
     *
     */
    public GetterController1(GetterModel model, GetterView view) {
        this.model = model;
        this.view = view;

        try {
            this.session = this.jssh.getSession(this.user, this.host,
                    this.port);
            this.session.setPassword(this.Password);
            this.session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("StrictHostKeyChecking is OFF");
            this.session.connect();
            System.out.println("Connected");

        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
         * Update view to reflect initial value of model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processRefreshEvent() {

        try {

            //Declare the thing to update the model
            Set<String> serverSet = new Set1L<>();

            //open the channel
            Channel channel = this.session.openChannel("sftp");
            channel.connect();

            //sftp version
            ChannelSftp channelSftp = (ChannelSftp) channel;
            channelSftp.cd(this.Mailbox);
            Vector filelist = channelSftp.ls(this.Mailbox);

            //pull the files and type cast to the LsEntry
            //this was taken from that stackoverflow thing
            //good idea
            for (int i = 0; i < filelist.size(); i++) {
                LsEntry entry = (LsEntry) filelist.get(i);

                //add to the set to get updates
                serverSet.add(entry.getFilename());
            }

            //remove the non-files
            serverSet.remove(".");
            serverSet.remove("..");

            //set the model to match

            this.model.setServerSet(serverSet);
            channel.disconnect();
        } catch (Exception e) {

        }

        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processToCart(String inputFile) {

        //Set<String> setter = this.model.cartSet();
        //setter.add(inputFile);
        //this.model.setCartSet(setter);
        this.model.addToCartSet(inputFile);
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processRemoveCartItem(String inFile) {
        //Set<String> remo = this.model.cartSet();
        //remo.remove(inFile);
        //this.model.setCartSet(remo);
        this.model.removeFromCartSet(inFile);
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processDownload() {
        //TODO
        //HOW TO GET THE SET OF WHAT IS IN THE CART
        //THEN IMPLEMENT THE SSH STUFF
        Set<String> remo = this.model.cartSet();
        System.out.println(remo.toString());

        int sizer = remo.size();

        try {
            //TODO
            //open the channel
            Channel channel = this.session.openChannel("sftp");
            channel.connect();
            //sftp version
            ChannelSftp channelSftp = (ChannelSftp) channel;
            channelSftp.cd(this.Mailbox);
            for (int i = 0; i < sizer; i++) {
                String curr = remo.removeAny();

                channelSftp.get(curr, "./");
            }

            //channelSftp.get();
            channel.disconnect();
        } catch (Exception e) {
        }

    }

    @Override
    public void processFileToUp() {

        String upFile;
        //Call the browser Here

        JFileChooser fileChooser = new JFileChooser(
                FileSystemView.getFileSystemView());
        //do the window
        fileChooser.showOpenDialog(null);

        //do if
        if (fileChooser.getSelectedFile() != null) {
            File fileIn = fileChooser.getSelectedFile();
            upFile = fileIn.getAbsolutePath();
            this.model.setDisplayFile(upFile);
        }

        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processFileSend() {
        String sendFile = this.model.displayFile();

        try {
            //open the channel
            Channel channel = this.session.openChannel("sftp");
            channel.connect();

            //sftp version
            ChannelSftp channelSftp = (ChannelSftp) channel;
            channelSftp.cd(this.Mailbox);

            //send file
            channelSftp.put(sendFile, this.Mailbox);

            channel.disconnect();

        } catch (Exception e) {

            System.out.println(
                    "The file didn't go, or connect to the server or whatever. Try again???");
        }

        updateViewToMatchModel(this.model, this.view);
    }

}

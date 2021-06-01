import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

//this class is just copied to display an image
@SuppressWarnings("serial")
public class ImageGetter extends Canvas {

    @Override
    public void paint(Graphics g) {

        Toolkit t = Toolkit.getDefaultToolkit();
        Image i = t.getImage("");
        g.drawImage(i, 120, 100, this);

    }
}

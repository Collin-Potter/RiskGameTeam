package PaymentDirectory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class JPane {
    private JPanel getPanel(){
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Transaction Menu");
        ImageIcon image = null;
        try{
            image = new ImageIcon(ImageIO.read(
                    new URL("")));
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        label.setIcon(image);
        panel.add(label);

        return panel;
    }
    public void displayGUI(){
        UIManager ui = new UIManager();

        ui.put("OptionPane.minimumSize",new Dimension(600,600));
        JOptionPane.showConfirmDialog(null,
                getPanel(),
                "Performing Transaction...",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
    }
}

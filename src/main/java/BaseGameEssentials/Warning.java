package BaseGameEssentials;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

public class
Warning implements Observer {

public Frame frame;
    public void Warn(String PlayerName, String Country, boolean test) {
        UIManager UI=new UIManager();

        UI.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 18));
        UI.put("OptionPane.minimumSize",new Dimension(600,200));
        UI.put("Button.font",new Font("Ms Sans Serif", Font.BOLD, 18));
        UI.put("OptionPane.background",new ColorUIResource(255, 49, 96));
        UI.put("Panel.background",new ColorUIResource(255, 146, 199));
        String Message =  PlayerName +", you will be attacked in "+ Country + ". :( \n"+
                         "you MUST click OK and face your enemy.";
     //   if(!test) {
           // JOptionPane.showMessageDialog(frame, Message, "Invasion BaseGameEssentials.Warning", JOptionPane.WARNING_MESSAGE);
       // }


    }
}

//Vasiliki Papadimou icsd14151

package lab_2_client;

import Messages.Message;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/*************************Δημιουργία Drop Down Menu πάνω από το κυρίο μενού μας*************************/

public class MainMenu extends JMenuBar{
    
    HotelClient hc; //αντικείμενο hc τύπου HotelClient 
    MainMenu(HotelClient hc){
        this.hc = hc;
        
        JMenu menu = new JMenu("Επιλογές");//"Δημιουργία" drop down
        
                
        //Υλοποίηση Menu About
        JMenuItem aboutmi = new JMenuItem("Σχετικά");
        //Πραγματοποιείται μία ενέργεια και "ακούγεται" κάπου κάνοντας κάτι
        aboutmi.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(hc,"Βίκυ","Σχετικά",JOptionPane.INFORMATION_MESSAGE );
            }
        });
        menu.add(aboutmi);

        //Υλοποίηση Menu Choice Exit
        JMenuItem exitmi = new JMenuItem("Έξοδος");
        exitmi.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //ΓΡΑΦΕΙ END ΣΤΟ ΠΑΡΆΘΥΡΟ ΤΟΥ CLIENT 
                    //oos ObjectOutputStream και γραφει στο object oos τoυ hc 
                    hc.oos.writeObject(new Message("END"));
                } catch (IOException ex) {
                    Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.exit(0);
            }
        });
        menu.add(exitmi);
        this.add(menu);
    }
}
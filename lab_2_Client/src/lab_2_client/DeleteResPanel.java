//Vasiliki Papadimou icsd14151

package lab_2_client;

import Messages.Message;
import Messages.Reservation;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class DeleteResPanel extends JPanel{
    HotelClient hc;
    
    //Εμφάνιση Παραθύσου Γραφικών για Ακυρωση Κρατησεων
    
    DeleteResPanel(HotelClient hc){
       this.hc = hc;
       this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
       
        JPanel idPanel = new JPanel();
        idPanel.setOpaque(true);
        JLabel idLabel = new JLabel("ID:"); //label 
        idLabel.setForeground(Color.darkGray);
        idPanel.add(idLabel); 
        JTextField idField = new JTextField(); //χώρος που γράφουμε τίτλο
        idField.setPreferredSize(new Dimension(100,20));
        idPanel.add(idField); //εμφάνιση Label και τίτλου που γράψαμε
        this.add(idPanel);
   
        /*
             ************* Γραφικό για Διαγραφή Καταγραφής *************
        */
        JPanel deletepanel = new JPanel();
        JButton deletebutton = new JButton(new ImageIcon("icons/delete.png"));
        
        deletebutton.addActionListener(new ActionListener(){ 
            @Override
            public void actionPerformed(ActionEvent e) {
            
                    String id = idField.getText();
                try {
                    Reservation res = new Reservation(id);
                    
                    Message m = new Message("DELETE",res);
                    hc.oos.writeObject(m);//γραψε στο αντικείμενο oos 
                    hc.oos.flush();// καθαριζει το αντικείμενο oos
                    
                    m = (Message) hc.ois.readObject();//Διάβασε την εγγραφή ois από το Message 
                   
                    // αν έφτασε σωστά τοτε ok
                    if (m.getMessage().equals("OK"))
                        JOptionPane.showMessageDialog(hc,"Επιτυχής Διαγραφή Κράτησης","Διαγραφή Κράτησης",JOptionPane.INFORMATION_MESSAGE );
                    else{
                        JOptionPane.showMessageDialog(hc,  "Ανεπιτυχής Διαγραφή Κράτησης","Διαγραφή Κράτησης",JOptionPane.INFORMATION_MESSAGE );
                    }
                    
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(InsertResPanel.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
            
        });
        deletepanel.add(deletebutton);
        this.add(deletepanel);
    }
}

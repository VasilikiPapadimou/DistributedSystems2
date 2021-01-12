//Vasiliki Papadimou icsd14151

package lab_2_client;

import Messages.Message;
import Messages.Reservation;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class DisplayPanel extends JPanel{
    HotelClient hc;
    
    //Εμφάνιση Παραθύρου Γραφικών για Εμφανιση Κρατησεων
    
    DisplayPanel(HotelClient hc){
       this.hc = hc;
       this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
       
        JPanel namepanel = new JPanel();
        namepanel.setOpaque(true);
        JLabel namelabel = new JLabel("Ονοματεπώνυμο:"); //label 
        namelabel.setForeground(Color.darkGray);
        namepanel.add(namelabel); 
        JTextField namefield = new JTextField(); //χώρος που γράφουμε τίτλο
        namefield.setPreferredSize(new Dimension(100,20));
        namepanel.add(namefield); //εμφάνιση Label και τίτλου που γράψαμε
        this.add(namepanel);
        
        JPanel arrivalpanel = new JPanel();
        arrivalpanel.setOpaque(true);
        JLabel arrivallabel = new JLabel("Άφιξη (yyyy-mm-dd): ");  
        arrivallabel.setForeground(Color.darkGray);
        arrivalpanel.add(arrivallabel); 
        JTextField arrivalfield = new JTextField(); //χώρος που γράφουμε τίτλο
        arrivalfield.setPreferredSize(new Dimension(100,20));
        arrivalpanel.add(arrivalfield); //εμφάνιση Label και τίτλου που γράψαμε
        this.add(arrivalpanel);
        
        /*
             ************* Γραφικό για Αναζήτηση Καταγραφής *************
        */
        JButton searchbutton = new JButton(new ImageIcon("icons/search.png"));
        
        searchbutton.addActionListener(new ActionListener(){ 
            @Override
            public void actionPerformed(ActionEvent e) {
            
                    String name = namefield.getText();
                    LocalDate arrival_Date;
                try {
                    arrival_Date = LocalDate.parse(arrivalfield.getText());
                    Reservation res = new Reservation(name,arrival_Date);
                    
                    Message m = new Message("SEARCH",res);  //ψαξε τo αντικειμενο res (δηλαδη ονομα και 
                    hc.oos.writeObject(m);  //και γραψε στο αντικείμενο oos 
                    hc.oos.flush(); //καθαριζει το αντικείμενο oos
                   
                    m = (Message) hc.ois.readObject();//Διάβασε την εγγραφή ois
                   
                    // αν έφτασε σωστά τοτε ok και κάνει εμφάνιση των αποτελεσμάτων του search
                    //δηλαδή όλα τα απαραίτητα στοιχεία κράτησης
                    if (m.getMessage().equals("OK")){
                        JOptionPane.showMessageDialog(hc,"ID Κράτησης: "+m.getReservation().getID()+'\n'+ 
                                "Τηλέφωνο πελάτη:"+m.getReservation().getPhone()+ '\n'+
                                "Ημ/νια Άφιξης(yyyy-mm-dd): " + m.getReservation().getArrivalDate()+'\n',
                                "Επιτυχής Αναζήτηση",JOptionPane.INFORMATION_MESSAGE );
                    }
                    else{
                        JOptionPane.showMessageDialog(hc,  "Ανεπιτυχής Αναζήτηση","Ανεπιτυχής Αναζήτηση",JOptionPane.INFORMATION_MESSAGE );
                    }
                    
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(InsertResPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DateTimeParseException ex){
                    JOptionPane.showMessageDialog(hc,  "Η ημερομηνία πρέπει να έχει την μορφή yyyy-mm-dd","Λάθος ημερομηνία",JOptionPane.INFORMATION_MESSAGE );
                } 
            }
            
        });
        this.add(searchbutton);
    }   
}

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

/* 
    ****************** ΔΗΜΙΟΥΡΓΊΑ PANEL ΕΙΣΑΓΩΓΗΣ ΣΤΟΙΧΕΙΩΝ ΚΡΑΤΗΣΗΣ  ******************
*/
public class InsertResPanel extends JPanel{
    
    HotelClient hc;
    InsertResPanel(HotelClient hc){
        this.hc = hc;
        
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        
        /*ΔΗΜΙΟΥΡΓΊΑ ΧΏΡΟΥ ΟΝΟΜΑΤΕΠΩΝΥΜΟΥ*/
        
        JPanel namepanel = new JPanel();
        namepanel.setOpaque(true);
        JLabel namelabel = new JLabel("Ονοματεπώνυμο:"); //label 
        namelabel.setForeground(Color.darkGray);
        namepanel.add(namelabel); 
        JTextField namefield = new JTextField(); 
        namefield.setPreferredSize(new Dimension(100,20));
        namepanel.add(namefield); 
        this.add(namepanel);
        
        /*ΔΗΜΙΟΥΡΓΊΑ ΧΩΡΟΥ ΤΗΛΕΦΩΝΟΥ*/

        JPanel phonepanel = new JPanel();
        phonepanel.setOpaque(true);
        JLabel phonelabel = new JLabel("Τηλέφωνο:");  
        phonelabel.setForeground(Color.darkGray);
        phonepanel.add(phonelabel); 
        JTextField phonefield = new JTextField(); 
        phonefield.setPreferredSize(new Dimension(100,20));
        phonepanel.add(phonefield); 
        this.add(phonepanel);
        
        /*ΔΗΜΙΟΥΡΓΊΑ ΧΩΡΟΥ ΗΜΕΡΟΜΗΝΊΑΣ ΑΦΙΞΗΣ*/
        
        JPanel arrivalpanel = new JPanel();
        arrivalpanel.setOpaque(true);
        JLabel arrivallabel = new JLabel("Άφιξη (yyyy-mm-dd):"); 
        arrivallabel.setForeground(Color.darkGray);
        arrivalpanel.add(arrivallabel); 
        JTextField arrivalfield = new JTextField(); 
        arrivalfield.setPreferredSize(new Dimension(100,20));
        arrivalpanel.add(arrivalfield); 
        this.add(arrivalpanel);
        
        /*ΔΗΜΙΟΥΡΓΊΑ ΧΩΡΟΥ ΗΜΕΡΟΜΗΝΊΑΣ ΑΝΑΧΩΡΗΣΗΣ*/
        
        JPanel departurepanel = new JPanel();
        departurepanel.setOpaque(true);
        JLabel departurelabel = new JLabel("Αναχώρηση (yyyy-mm-dd):"); 
        departurelabel.setForeground(Color.darkGray);
        departurepanel.add(departurelabel); 
        JTextField departurefield = new JTextField(); 
        departurefield.setPreferredSize(new Dimension(100,20));
        departurepanel.add(departurefield); 
        this.add(departurepanel);
        
        /*ΔΗΜΙΟΥΡΓΊΑ ΧΩΡΟΥ ΜΕΝΟΥ ΕΠΙΛΟΓΗΣ ΤΥΠΟY ΔΩΜΑΤΊΟΥ */
        
        JPanel typepanel = new JPanel();
        typepanel.setOpaque(true);
        JLabel typelabel = new JLabel("Τύπος:"); //label 
        typelabel.setForeground(Color.darkGray);
        typepanel.add(typelabel); 
        String []type ={"Μονόκλινο","Δίκλινο","Τρίκλινο"}; //τύπος δωματίου 
        JComboBox typebox = new JComboBox(type);// dropdown list
        typepanel.add(typebox);//προσθήκη box μενου για τύπους δωματίων
        this.add(typepanel);

        /*ΔΗΜΙΟΥΡΓΊΑ ΧΩΡΟΥ CHECKBOX ΜΕ/ΧΩΡΙΣ ΠΡΩΙΝΟ*/
        
        JPanel breakfastpanel = new JPanel();
        breakfastpanel.setOpaque(true);
        JLabel breakfastlabel = new JLabel("Πρωινό:"); //label 
        breakfastlabel.setForeground(Color.darkGray);
        breakfastpanel.add(breakfastlabel); 
        JCheckBox breakfastbox = new JCheckBox(); //checkbox αν θελει με πρωινο κρατηση
        breakfastbox.setSelected(true);//default επιλογη να εχει πρωινο
        breakfastpanel.add(breakfastbox);
        this.add(breakfastpanel);
        
        /* ΓΡΑΦΙΚΟ ΓΙΑ ΑΠΟΣΤΟΛΗ ΚΡΑΤΗΣΗΣ */
        
        JButton insertbutton = new JButton(new ImageIcon("icons/insert.png"));
        // ΤΙ ΘΑ ΚΑΝΕΙ ΑΥΤΟ ΤΟ ΚΟΥΜΠΙ
        insertbutton.addActionListener(new ActionListener(){ 
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //Ορίζουμε τα πεδία που πρέπει να παίρνει απο το res και τα κάνουμε INSERT απο το res στο 
                    //"μεσσολαβητή" messages
                    String name = namefield.getText();
                    String phone = phonefield.getText();
                    LocalDate afixiDate,departDate;
                    afixiDate = LocalDate.parse(arrivalfield.getText());
                    departDate = LocalDate.parse(departurefield.getText());
                    String type = (String) typebox.getSelectedItem();
                    boolean hasbreakfast = breakfastbox.isSelected();
                     
                    Reservation res = new Reservation(name,phone,afixiDate,departDate,type,hasbreakfast);
                    Message m = new Message("INSERT",res);
                    hc.oos.writeObject(m);// γραψε στο αντικείμενο oos 
                    hc.oos.flush();// καθαριζει το αντικείμενο oos
                    m = (Message) hc.ois.readObject();//Διάβασε την εγγραφή ois
                    
                    if (m.getMessage().equals("OK")){ // αν έφτασε σωστά τοτε ok
                        
                        // ID για να μπορούμε να καταλάβουμε που αναφερόμαστε 
                        String id = m.getReservation().getID();
                        
                        //String name = m.getReservation().getName();
                        JOptionPane.showMessageDialog(hc,  "Επιτυχής Κράτηση - ID: " + id + '\n'+
                                " Ον/μο Πελάτη: " + name + '\n'+
                                " Τηλέφωνο Πελάτη: " + phone + '\n'+
                                " Ημ/νια Άφιξης(yyyy-mm-dd): " + afixiDate + '\n'+
                                " Ημ/νια Αναχώρησης(yyyy-mm-dd): " + departDate + '\n'+
                                " Τύπος Δωματίου: " + type + '\n'+
                                " Πρωινό (true αν έχει πρωινό): " + hasbreakfast + '\n'+
                                " Κόστος Κράτησης: " + m.getReservation().getCost(),
                                " Επιτυχής Κράτηση",JOptionPane.INFORMATION_MESSAGE );
                    }
                    else{
                        JOptionPane.showMessageDialog(hc,  "Ανεπιτυχής Κράτηση","Ανεπιτυχής Κράτηση",JOptionPane.INFORMATION_MESSAGE );
                    }
                    
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(InsertResPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                catch (DateTimeParseException ex){
                    JOptionPane.showMessageDialog(hc,  "Η ημερομηνία πρέπει να έχει την μορφή yyyy-mm-dd","Λάθος ημερομηνία",JOptionPane.INFORMATION_MESSAGE );
                } 
            }          
        });
        this.add(insertbutton);
    }
}

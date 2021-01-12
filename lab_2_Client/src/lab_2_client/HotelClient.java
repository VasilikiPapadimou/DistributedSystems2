//Vasiliki Papadimou icsd14151

package lab_2_client;

import Messages.Message;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import javax.swing.*;


public class HotelClient extends JFrame{
    InsertResPanel insertpanel;   // Εισαγωγή κράτησης
    DisplayPanel displaypanel;    // Αναζήτηση κράτησης
    DeleteResPanel deletepanel;   //Διαγραφή κράτησης
    ObjectOutputStream oos;
    ObjectInputStream ois;
    
    HotelClient(){
    
    /* 
      *********************** MYNHMATA CLIENT -> SERVER *********************** 
    */
        try {
            //Ανοιξε την θύρα 5555 και στειλε το μηνυμά μου στο localhost μέσω socket
            Socket sock = new Socket("localhost", 5555); 
            oos = new ObjectOutputStream(sock.getOutputStream()); //Δεσμευση χώρου για να παρει το oos στο socket
            ois = new ObjectInputStream(sock.getInputStream());
            oos.writeObject(new Message("START"));  //Μύνημα Client προς Server
            oos.flush(); //Καθαρισμός του ObjectOutputStream
            
            Message m = (Message) ois.readObject(); //"Mεσολαβητής" Message για να μην συμπεσουν τα μηνύματα στο socket κατα το read
            
            //Απάντηση Server στον Client 
            if (m.getMessage().equals("WAITING")) 
                JOptionPane.showMessageDialog(this,  "Σύνδεση στον εξυπηρετητή."+'\n'+" Αναμονή για επικοινωνία","Επιτυχής Σύνδεση",JOptionPane.INFORMATION_MESSAGE );
        }catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this,  "Το αντικείμενο από τον εξυπηρετητή δεν διαβάστηκε επιτυχώς","Αποτυχία Διαβάσματος Αντικειμένου",JOptionPane.INFORMATION_MESSAGE );
        }
    /*------------------------------------------------------------------------------------------------------------*/
        this.setTitle("ΣΥΣΤΥΜΑ ΚΡΑΤΗΣΕΩΝ");        
        this.setVisible(true);
        this.setSize(450,350);
        
        MainMenu mb = new MainMenu(this); // Χώρος Drop Down Menu (υλοποιηση στην MainMenu)

        /* TABBED MENU Εισαγωγή, Εμφάνιση και Ακύρωση Κράτησης */
        this.setJMenuBar(mb);  
        JTabbedPane tabbedPane = new JTabbedPane();
        this.add(tabbedPane); 
        
        insertpanel = new InsertResPanel(this); 
        tabbedPane.add(insertpanel,"Εισαγωγή Κράτησης");
        displaypanel = new DisplayPanel(this); 
        tabbedPane.add(displaypanel,"Αναζήτηση Κράτησης");
        deletepanel = new DeleteResPanel(this); 
        tabbedPane.add(deletepanel,"Ακύρωση Κράτησης");
        
        //Στέλνει μήνυμα στον server και σταματάει να τρέχει με το που κλείνω το παράθυρο
        //(Διαφορετικά βγάζει exeption το πρόγραμμα και θέλει κάθε φορα να κλείνουμε και Server και Client για να τρέξει)
        addWindowListener(new WindowAdapter(){  //innerclass
            @Override
            public void windowClosing(WindowEvent e){
                try {
                    Message m = new Message("END");// μήνυμα κατα τον τερματισμό socket
                    oos.writeObject(m);//γραφει στο αντικείμενο oos
                    oos.flush();// καθαριζει το αντικείμενο oos
                    System.exit(0);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null,  "Ανεπιτυχής αποσύνδεση από τον εξυπηρετητή.","Ανεπιτυχής απούνδεση",JOptionPane.INFORMATION_MESSAGE );
                }
            }
        });
    }
}

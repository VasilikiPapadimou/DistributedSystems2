//Vasiliki Papadimou icsd14151
package lab_2_server;

import Messages.Message;
import Messages.Reservation;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMain {
    //Λίστα που θα δέχεται τα στοιχεία των κρατήσεων από την Messages 
    static ArrayList<Reservation> reservations = new ArrayList<>();
    static int id=0;
    
    public static void main(String[] args) {
        ObjectOutputStream oos;
        ObjectInputStream ois;
        try {
            //Message m = new Message("WAITING");
            ServerSocket server = new ServerSocket(5555);
            
            while (true){
                System.out.println("Accepting Connection..."); // Ανοιξε ο Server 
                Socket sock = server.accept(); //και Περιμένει τον Client να κάνει αποδοχή το αίτημα επικοινωνίας
                System.out.println("Connection accepted!");// Συνδεθηκε ο Client
                oos = new ObjectOutputStream(sock.getOutputStream());//χώρος για τα outputStreams από το socket
                ois = new ObjectInputStream(sock.getInputStream());//χώρος για τα inputStreams από το socket
                
                Message m = (Message)ois.readObject(); // διαβάζει το οis που πήγε στο Message για να το στείλει 
                                                       //εκεί που πρέπει κάθε φορά 
                
                oos.writeObject(new Message("WAITING")); // Απάντηση Server στο oos 
                oos.flush(); 
                while(true){
                   m = (Message)ois.readObject();
                   
                    //ο Client όταν στέλνουμε μήνυμα για Insert
                   if (m.getMessage().equals("INSERT")){
                       // Δημιουργία αντικειμένου κράτησης στον εξυπρετητή
                       Reservation res = new Reservation(m.getReservation().getName(),m.getReservation().getPhone(),
                               m.getReservation().getArrivalDate(),m.getReservation().getDepartDate(),
                                    m.getReservation().getType(),m.getReservation().hasBreakfast());
                       // Ανάθεση σε αυτήν μοναδικού id (άυξων αριθμός)
                       res.setID(id++);
                       // Προσθήκη στην λίστα κρατήσων του εξυπρετητή
                       reservations.add(res);
                       // Αποστολή αντικειμένου κράτησης με στοιχείο το id
                       oos.writeObject(new Message("OK",new Reservation(res.getID(),res.calculateCost())));
                       oos.flush();
                   }
                   
                    //ο Client όταν στέλνουμε μήνυμα για Search
                   if (m.getMessage().equals("SEARCH")){
                       Reservation r = null;
                       //για κάθε res αντικειμενο της λίστας reservations 
                        for (Reservation res:reservations){ 
                            //έλεγχος για αναζήτηση στοιχείων κράτησης αν ταιρίαζουν αυτα του res με του m.
                            if (res.getName().equals(m.getReservation().getName())
                                && res.getArrivalDate().equals(m.getReservation().getArrivalDate())){
                               r = res;
                               break;
                           }
                       }
                       if (r!=null){
                          oos.writeObject(new Message("OK",r));//αν τα στοιχεία κάνουν match τότε το Search μας πήγε καλά
                          oos.flush(); //και καθάρισε το oos
                       }
                       else{
                            oos.writeObject(new Message("NOT OK"));// αντίθετως το match δεν πέτυχε αρα και το Search μας.
                            oos.flush();
                       }
                   }
                   
                    //ο Client όταν στέλνουμε μήνυμα για Delete
                   if (m.getMessage().equals("DELETE")){
                       boolean deleted=false;
                       String id = m.getReservation().getID(); 
                       //για κάθε res αντικειμενο της λίστας reservations 
                       for (Reservation res:reservations){
                           if (res.getID().equals(id)){// αν το id είναι ίδιο στο res και στην λίστα 
                               reservations.remove(res);//Αφαίρεσέ το 
                               deleted = true;// διαγράφηκε
                               break;
                           }
                       }
                       if (deleted){
                          oos.writeObject(new Message("OK"));//αν deleted έιναι true τότε η Διαγραφή  πήγε καλά
                          oos.flush(); 
                       }
                       else{
                            oos.writeObject(new Message("NOT OK"));
                            oos.flush();
                       }
                   }
                   else if (m.getMessage().equals("END"))
                       break;
                }
                
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

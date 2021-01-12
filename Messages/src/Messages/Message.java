//Vasiliki Papadimou icsd14151

package Messages;

import java.io.Serializable;
//Στην κλάση αυτή υλοποιούμε κώδικα για την σύνδεση των Client/Server
//Διανέμουμε τα μηνυματα έτσι ώστε να καταφέρουν να φτάσουν χωρίς να χαθούν
//Είναι μία κλάση "μεσολαβητής" 
public class Message implements Serializable{
    private String str;
    Reservation res;
    
    public Message(String str){
        this.str = str;
        res = null;
    }
    public Message(String str,Reservation res){
        this.str = str;
        this.res = res;
    }
    public Reservation getReservation(){
        return this.res;
    }
    public String getMessage(){
        return this.str;
    }
}
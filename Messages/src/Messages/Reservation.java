//Vasiliki Papadimou icsd14151

package Messages;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import static java.time.temporal.ChronoUnit.DAYS;

public class Reservation implements Serializable{
    
    private String name;
    private String phone;
    private LocalDate arrivalDate, departDate;
    private String type;   
    private boolean hasbreakfast;
    private String id;
    int cost;
    
    // Κατασκευαστής κράτησης
    public Reservation(String name,String phone,LocalDate arrivalDate,LocalDate departDate,
            String type, boolean hasbreakfast){
        this.name = name;
        this.phone = phone;
        this.arrivalDate=arrivalDate;
        this.departDate=departDate;
        this.type = type;
        this.hasbreakfast = hasbreakfast;
    }
    // Κατασκευαστής μόνο για αναζήτηση 
    public Reservation(String name,LocalDate arrival_Date){
        this.name = name;
        this.arrivalDate=arrival_Date;        
    }
    // Κατασκευαστής μόνο για διαγραφή κράτησης
    public Reservation(String id){
        this.id=id;
    }
    // Κατασκευαστής μόνο για αποστολή κόστους και id στον πελάτη
    public Reservation(String id,int cost){
        this.id=id;
        this.cost = cost;
        
    }
    public int getCost(){
        return this.cost;
    }
    public int calculateCost(){
        int days = (int)DAYS.between(arrivalDate, departDate);
        // Η μέρα άφιξης καθορίζει αν είναι σε υψηλή η χαμηλή τουριστική περίοδο
        if (arrivalDate.getMonth()==Month.JUNE || arrivalDate.getMonth()==Month.JULY || 
                arrivalDate.getMonth()==Month.AUGUST || arrivalDate.getMonth()==Month.SEPTEMBER){
            if (type.equals("Μονόκλινο")){
                if (hasbreakfast)
                    return 88*days;
                else
                    return 80*days;
            }
            else if (type.equals("Δίκλινο")){
                if (hasbreakfast)
                    return 136*days;
                else
                    return 120*days;
            }
            else{
                if (hasbreakfast)
                    return 174*days;
                else
                    return 150*days;
            }
        }
        else{
            if (type.equals("Μονόκλινο")){
                if (hasbreakfast)
                    return 48*days;
                else
                    return 40*days;
            }
            else if (type.equals("Δίκλινο")){
                if (hasbreakfast)
                    return 86*days;
                else
                    return 70*days;
            }
            else{
                if (hasbreakfast)
                    return 109*days;
                else
                    return 85*days;
            }
        }
    }
    public String getName(){
        return name;
    }
    public String getPhone(){
        return phone;
    }
    public LocalDate getArrivalDate(){
        return arrivalDate;
    }
    public LocalDate getDepartDate(){
        return departDate;
    }
    public String getID(){
        return this.id;
    }
    public void setID(int id){
        if (id<10)
            this.id = "ID00"+id;
        else if (id<100)
            this.id = "ID0"+id;
        else
            this.id = "ID" + id;
    }
    public String getType() {
        return type;
    }

    public boolean hasBreakfast() {
        return hasbreakfast;
    }
    public void setHasBreakfast(boolean hasbreakfast){
         if(this.hasbreakfast==true){System.out.print("Με πρωινο");}
         else{System.out.print("Χωρις πρωινο");}
         
    }
}

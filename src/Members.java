import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public  class Members implements Serializable {

    String Name;
    String Email;
    LocalDate DueDate;
   public  ArrayList<String> borrowedBooks = new ArrayList<>();
    public boolean notificationsEnabled; // New field to store notification preference

    public Members(String Name,String Email ){
        this.Email = Email;
        this.Name = Name;
        this.notificationsEnabled = true; // Default to true (notifications enabled)
    }

}

import java.util.ArrayList;
import java.util.List;

public  class Members {

    String Name;
    String Email;
   public  ArrayList<String> borrowedBooks = new ArrayList<>();

    public Members(String Name,String Email ){
        this.Email = Email;
        this.Name = Name;
    }

}

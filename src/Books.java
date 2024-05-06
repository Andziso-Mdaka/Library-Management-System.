import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public  class Books implements Serializable {
    String Title;
    String Author;
    String ISBN;
    boolean IsAvailable;

    public Books(String Title, String Author, String ISBN,boolean IsAvailable){
        this.Author = Author;
        this.ISBN = ISBN;
        this.Title = Title;
        this.IsAvailable = IsAvailable;

    }



}
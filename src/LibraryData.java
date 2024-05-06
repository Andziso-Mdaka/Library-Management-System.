import java.io.*;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LibraryData implements Serializable {
    public List<Books> bookCollection;
    public List<Members> memberCollection;
    public List<Transaction> transactionList;

    public LibraryData() {
        this.bookCollection = new ArrayList<>();
        this.memberCollection = new ArrayList<>();
        this.transactionList = new ArrayList<>();
    }

    public static class Transaction implements Serializable {
        public String memberName;
        public String bookTitle;
        public LocalDate transactionDate;
        public TransactionType transactionType;

        public Transaction(String memberName, String bookTitle, LocalDate transactionDate, TransactionType transactionType) {
            this.memberName = memberName;
            this.bookTitle = bookTitle;
            this.transactionDate = transactionDate;
            this.transactionType = transactionType;
        }
    }

    public enum TransactionType implements Serializable {
        CHECKOUT,
        RETURN
    }

    private static final String DATA_FILE = "library_data.ser";

    public static void saveLibraryData(LibraryData libraryData) {
        try {

            FileOutputStream fileOut = new FileOutputStream(DATA_FILE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(libraryData);
            out.close();
            fileOut.close();
            System.out.println("Library data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LibraryData loadLibraryData() {
        LibraryData libraryData = null;
        try {
            FileInputStream fileIn = new FileInputStream(DATA_FILE);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            libraryData = (LibraryData) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Library data loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No saved data found. Creating new library data.");
            libraryData = new LibraryData();
        }
        return libraryData;
    }


}
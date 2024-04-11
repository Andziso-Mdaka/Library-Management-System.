
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static ArrayList<Books> BookCollection = new ArrayList<Books>();
    static ArrayList<Members> MemberCollection = new ArrayList<Members>();

   public static void SetBooks(){

       Books book1 = new Books("Skibidi Ohio", "Junior", "12345678",true);
       Books book2 = new Books("Game of Snow","Ash", "scascas",true);
       Books book3 = new Books("Harambe Documentary"," Yandi"," 545",true);
       Books book4 = new Books("How to steal","Martin","0000000",true);
       Books book5 = new Books("Lisan Al Gaib","Andzi","0000012",true);



       BookCollection.add(book1);
       BookCollection.add(book2);
       BookCollection.add(book3);
       BookCollection.add(book4);
       BookCollection.add(book5);


   }

   public static void SetMembers(){

       Members member1 = new Members("walter","walter@gmail.com");
       Members member2 = new Members("andzi","andzi@gmail.com");
       Members member3 = new Members("junior","junior@gmail.com");
       Members member4 = new Members("ash","ash@gmail.com");
       Members member5 = new Members("martin","martin@gmail.com");

       MemberCollection.add(member1);
       MemberCollection.add(member2);
       MemberCollection.add(member3);
       MemberCollection.add(member4);
       MemberCollection.add(member5);

   }

    public static boolean validateEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void AddMember() {
        System.out.println("Enter the Member Name");
        Scanner MemberName = new Scanner(System.in);
        String Mname = MemberName.nextLine();

        String Memail = "";
        boolean isValidEmail = false;
        while (!isValidEmail) {
            System.out.println("Enter the Member Email");
            Scanner MemberEmail = new Scanner(System.in);
            Memail = MemberEmail.nextLine();
            isValidEmail = validateEmail(Memail);
            if (!isValidEmail) {
                System.out.println("Invalid email format. Please try again.");
            }
        }


        Members member = new Members(Mname, Memail);

        MemberCollection.add(member);
    }

    public static boolean Availability() {

        System.out.println("Is the book available (Y/N)");
        Scanner BookAvailable = new Scanner(System.in);
        String BAvailble = BookAvailable.nextLine().toLowerCase();

        boolean BAvailblility = false;

        switch (BAvailble) {

            case "y":
                BAvailblility = true;

                break;
            case "n":
                break;

            default:
                System.out.println("Invalid input, Please try again.");
                Availability();
                break;

        }

        return BAvailblility;
    }

    public static void AddBook() {
        System.out.println("Enter Book Name: ");
        Scanner BookTitle = new Scanner(System.in);
        String BTitle = BookTitle.nextLine();

        System.out.println("Enter Book Author: ");
        Scanner BookAuthor = new Scanner(System.in);
        String BAuthor = BookAuthor.nextLine();

        System.out.println("Enter Book ISBN: ");
        Scanner BookISBN = new Scanner(System.in);
        String BISBN = BookISBN.nextLine();

        boolean Bavailable = Availability();

        Books book = new Books(BTitle, BAuthor, BISBN, Bavailable);
        BookCollection.add(book);
    }

    public static void Search() {
        System.out.println("Search for Member or Book");
        System.out.println("1. Book");
        System.out.println("2. Member");
        System.out.println(" ");

        Scanner inputScanner = new Scanner(System.in);
        String input = inputScanner.nextLine();


        switch (input) {
            case "1":
                System.out.println("Enter the Book Title/Author");
                Scanner Binput = new Scanner(System.in);
                String search = Binput.nextLine().toLowerCase();

                for (Books book : BookCollection) {
                    if (book.Title.toLowerCase().contains(search) || book.Author.toLowerCase().contains(search)) {
                        System.out.println(book.Title + ", By " + book.Author);
                    }
                }
                System.out.println(" ");
                break;

            case "2":
                System.out.println("Enter the Member Email/Name");
                Scanner Minput = new Scanner(System.in);
                String Membersearch = Minput.nextLine().toLowerCase();

                for (Members member : MemberCollection) {
                    if (member.Name.toLowerCase().contains(Membersearch) || member.Email.toLowerCase().contains(Membersearch)) {
                        System.out.println(member.Name + ", " + member.Email);
                    }
                }
                System.out.println(" ");
                break;
        }
    }

    public static void DisplayMembers() {
        int counter = 1;
        for (Members member : MemberCollection) {
            System.out.println(counter + ". " + member.Name + ", " + member.Email);
            if (!member.borrowedBooks.isEmpty()) {
                System.out.println("Borrowed Books:");
                for (String book : member.borrowedBooks) {
                    System.out.println("- " + book);
                }
            } else {
                System.out.println("No borrowed books");
            }
            System.out.println();
            counter++;
        }
        System.out.println();
    }
    public static void DisplayBooks(){
        int counter = 1;
        for (Books book : BookCollection){

            System.out.println(counter + "." +book.Title  + " by " + book.Author );
            counter = counter + 1;

        }
        System.out.println(" ");
    }

    public static void BookCheckout() {

        System.out.println("Available Books:");
        DisplayBooks();

        System.out.println("Enter the number of the book you want to checkout:");
        Scanner inputScanner = new Scanner(System.in);
        int choice = inputScanner.nextInt();


        if (choice < 1 || choice > BookCollection.size()) {
            System.out.println("Invalid book selection. Please try again.");
            return;
        }

        Books selectedBook = BookCollection.get(choice - 1);


        if (selectedBook.IsAvailable) {

            System.out.println("Enter your member email:");
            String memberEmail = inputScanner.next();

            Members member = null;
            for (Members m : MemberCollection) {
                if (m.Email.equals(memberEmail)) {
                    member = m;
                    break;
                }
            }

            if (member != null) {
                member.borrowedBooks.add(selectedBook.Title);
                selectedBook.IsAvailable = false;
                System.out.println("Book checked out successfully for " + member.Name);
            } else {
                System.out.println("Member not found.");
            }
        } else {
            System.out.println("Sorry, the selected book is already checked out.");
        }
    }


    public static void BookReturn() {
        System.out.println("Members:");
        DisplayMembers();

        System.out.println("Enter your email to return a book:");
        Scanner inputScanner = new Scanner(System.in);
        String memberEmail = inputScanner.nextLine();

        Members member = null;
        for (Members m : MemberCollection) {
            if (m.Email.equals(memberEmail)) {
                member = m;
                break;
            }
        }

        if (member != null) {
            System.out.println("Your borrowed books:");
            for (String book : member.borrowedBooks) {
                System.out.println("- " + book);
            }

            System.out.println("Enter the name of the book you want to return:");
            String bookToReturn = inputScanner.nextLine();

            if (member.borrowedBooks.remove(bookToReturn)) {
                for (Books book : BookCollection) {
                    if (book.Title.equals(bookToReturn)) {
                        book.IsAvailable = true;
                        System.out.println("Book returned successfully.");
                        return;
                    }
                }
            } else {
                System.out.println("You have not borrowed that book.");
            }
        } else {
            System.out.println("Member not found.");
        }
    }

    public static void main(String[] args) {

        SetBooks();
        SetMembers();


        while (true){

         System.out.println(" Welcome to the Manga library");
         System.out.println("1. Register Member");
         System.out.println("2. Add Book");
         System.out.println("3. Search Book/Member");
         System.out.println("4. Book Checkout");
         System.out.println("5. Book Return");
         System.out.println("6. Library Catalogue");
         System.out.println("7. Library Members");
         System.out.println("8. Exit");
         System.out.println(" ");


         Scanner mainInputScanner = new Scanner(System.in);
         String input = mainInputScanner.nextLine();

         switch (input) {
             case "1":
                 AddMember();
                 break;
             case "2":
                 AddBook();

                 break;
             case "3":
                 Search();

                 break;
             case "4":
                 BookCheckout();

                 break;
             case "5":
                 BookReturn();

                 break;
             case "6":
                 DisplayBooks();

                 break;
             case "7":
                 DisplayMembers();

                 break;
             case "8":
                 System.exit(0);
                 break;
             default:
                 System.out.println("Invalid input, please try again");

         }
     }

    }
}

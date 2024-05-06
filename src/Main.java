import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    // Collections to store books and members
    static ArrayList<Books> BookCollection = new ArrayList<Books>();
    static ArrayList<Members> MemberCollection = new ArrayList<Members>();

    // Method to set up initial book collection
    public static void SetBooks(){
        // Create and add books to the collection
        Books book1 = new Books("Skibidi Ohio", "Junior", "12345678",true);
        Books book2 = new Books("Game of Snow","Ash", "scascas",true);
        Books book3 = new Books("Harambe Documentary"," Yandi"," 545",true);
        Books book4 = new Books("How to steal","Martin","0000000",true);
        Books book5 = new Books("Lisan Al Gaib","Andzi","0000012",true);

        libraryData.bookCollection.add(book1);
        libraryData.bookCollection.add(book2);
        libraryData.bookCollection.add(book3);
        libraryData.bookCollection.add(book4);
        libraryData.bookCollection.add(book5);
    }

    // Method to set up initial member collection
    public static void SetMembers(){
        // Create and add members to the collection
        Members member1 = new Members("walter","walter@gmail.com");
        Members member2 = new Members("andzi","andzi@gmail.com");
        Members member3 = new Members("junior","junior@gmail.com");
        Members member4 = new Members("ash","ash@gmail.com");
        Members member5 = new Members("martin","martin@gmail.com");

        libraryData.memberCollection.add(member1);
        libraryData.memberCollection.add(member2);
        libraryData.memberCollection.add(member3);
        libraryData.memberCollection.add(member4);
        libraryData.memberCollection.add(member5);
    }

    // Method to validate email format
    public static boolean validateEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Method to add a new member
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
        libraryData.memberCollection.add(member);
        System.out.println("member has been added");
    }

    // Method to check book availability
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

    // Method to add a new book
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
        libraryData.bookCollection.add(book);
        System.out.println("book has been added");
    }

    // Method to search for books or members
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

                for (Books book :  libraryData.bookCollection) {
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

                for (Members member :  libraryData.memberCollection) {
                    if (member.Name.toLowerCase().contains(Membersearch) || member.Email.toLowerCase().contains(Membersearch)) {
                        System.out.println(member.Name + ", " + member.Email);
                    }
                }
                System.out.println(" ");
                break;
        }
    }

    // Method to display all members and their borrowed books
    public static void DisplayMembers() {
        int counter = 1;
        for (Members member : libraryData.memberCollection) {
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

    // Method to display all books in the library
    public static void DisplayBooks(){
        int counter = 1;
        for (Books book :  libraryData.bookCollection){
            System.out.println(counter + "." +book.Title  + " by " + book.Author );
            counter = counter + 1;
        }
        System.out.println(" ");
    }

    // Method to check out a book
    public static void BookCheckout() {
        System.out.println("Available Books:");
        DisplayBooks();
        // book selection to check out
        System.out.println("Enter the number of the book you want to checkout:");
        Scanner inputScanner = new Scanner(System.in);
        try {
            int choice = inputScanner.nextInt();

            if (choice < 1 || choice >  libraryData.bookCollection.size()) {
                System.out.println("Invalid book selection. Please try again.");
                return;
            }

            Books selectedBook =  libraryData.bookCollection.get(choice - 1);

            if (selectedBook.IsAvailable) {
                System.out.println("Enter your member email:");
                String memberEmail = inputScanner.next();
                // getting specific member
                Members member = null;
                LocalDate currentDate = LocalDate.now();
                for (Members m :  libraryData.memberCollection) {
                    if (m.Email.equals(memberEmail)) {
                        member = m;
                        // Get the current date
                        // Add days, months, or years as needed
                        member.DueDate = currentDate.plusDays(14);
                        break;
                    }

                }
                // adding book
                if (member != null) {
                    member.borrowedBooks.add(selectedBook.Title);
                    selectedBook.IsAvailable = false;
                    System.out.println("Book checked out successfully for " + member.Name + " on " + currentDate);
                    System.out.println("Return the book by " + member.DueDate);

                    // Add transaction log
                    LibraryData.Transaction checkoutTransaction = new LibraryData.Transaction(member.Name, selectedBook.Title, currentDate, LibraryData.TransactionType.CHECKOUT);
                    libraryData.transactionList.add(checkoutTransaction);
                } else {
                    System.out.println("Member not found.");
                }
            } else {
                System.out.println("Sorry, the selected book is already checked out.");
            }

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            inputScanner.nextLine(); // Clear the input
        }


    }

    // Method to calculate overdue fines
    public static double calculateOverdueFines(LocalDate dueDate) {
        // Define a constant for the fine rate per day
        final double FINE_RATE_PER_DAY = 0.5; // Example fine rate: $0.50 per day

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Calculate the number of days overdue
        long daysOverdue = ChronoUnit.DAYS.between(dueDate, currentDate);

        // Calculate the fine amount
        double fineAmount = Math.max(0, daysOverdue) * FINE_RATE_PER_DAY; // Ensure fine amount is non-negative

        return fineAmount;
    }


    // Method to return a book
    public static void BookReturn() {
        System.out.println("Members:");
        DisplayMembers();
        // get email for member to return book
        System.out.println("Enter your email to return a book:");
        Scanner inputScanner = new Scanner(System.in);
        String memberEmail = inputScanner.nextLine();
        LocalDate currentDate = LocalDate.now();

        // get specific member
        Members member = null;
        for (Members m :  libraryData.memberCollection) {
            if (m.Email.equals(memberEmail)) {
                member = m;
                break;
            }
        }

        // print list of borrowed books
        if (member != null) {
            System.out.println("Your borrowed books:");
            for (String book : member.borrowedBooks) {
                System.out.println("- " + book);
            }

            System.out.println("Enter the name of the book you want to return:");
            String bookToReturn = inputScanner.nextLine();

            // remove book
            if (member.borrowedBooks.remove(bookToReturn)) {
                for (Books book :  libraryData.bookCollection) {
                    if (book.Title.equals(bookToReturn)) {
                        double fineAmount = calculateOverdueFines(member.DueDate);
                        if (fineAmount > 0) {
                            System.out.println("Book returned successfully. Overdue fines: $" + fineAmount);
                        } else {
                            System.out.println("Book returned successfully.");
                        }
                        book.IsAvailable = true;

                        // Add transaction log
                        LibraryData.Transaction returnTransaction = new LibraryData.Transaction(member.Name, bookToReturn, currentDate, LibraryData.TransactionType.RETURN);
                        libraryData.transactionList.add(returnTransaction);
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

    public static void DisplayTransactions() {
        System.out.println("Transaction History:");
        for (LibraryData.Transaction transaction : libraryData.transactionList) {
            String transactionType = transaction.transactionType == LibraryData.TransactionType.CHECKOUT ? "Checkout" : "Return";
            System.out.println(transactionType + ": " + transaction.memberName + " - " + transaction.bookTitle + " (" + transaction.transactionDate + ")");
        }
        System.out.println();
    }

    private static Books GetBookByTitle(String title) {
        for (Books book : Main.libraryData.bookCollection) {
            if (book.Title.equals(title)) {
                return book;
            }
        }
        return null;
    }

    // Method to check due dates for a member
    public static void CheckDueDates() {
        System.out.println("Enter your member email to check due dates:");
        Scanner inputScanner = new Scanner(System.in);
        String memberEmail = inputScanner.nextLine();

        Members member = null;
        for (Members m : libraryData.memberCollection) {
            if (m.Email.equals(memberEmail)) {
                member = m;
                break;
            }
        }

        if (member != null) {
            System.out.println("Due Dates for " + member.Name + ":");
            for (String bookTitle : member.borrowedBooks) {
                Books book = GetBookByTitle(bookTitle);
                if (book != null) {
                    LocalDate dueDate = member.DueDate;
                    System.out.println("- " + bookTitle + ": " + dueDate);
                }
            }
        } else {
            System.out.println("Member not found.");
        }
        System.out.println();
    }

    // Method to view fines for a member
    public static void ViewFines() {
        System.out.println("Enter your member email to view fines:");
        Scanner inputScanner = new Scanner(System.in);
        String memberEmail = inputScanner.nextLine();

        Members member = null;
        for (Members m : libraryData.memberCollection) {
            if (m.Email.equals(memberEmail)) {
                member = m;
                break;
            }
        }

        if (member != null) {
            double totalFine = 0.0;
            for (String bookTitle : member.borrowedBooks) {
                Books book = GetBookByTitle(bookTitle);
                if (book != null) {
                    LocalDate dueDate = member.DueDate;
                    double fineAmount = calculateOverdueFines(dueDate);
                    totalFine += fineAmount;
                }
            }
            System.out.println("Total fines for " + member.Name + ": $" + totalFine);
        } else {
            System.out.println("Member not found.");
        }
        System.out.println();
    }

    // Method to manage notifications
    public static void ManageNotifications() {
        System.out.println("Enter your member email to manage notifications:");
        Scanner inputScanner = new Scanner(System.in);
        String memberEmail = inputScanner.nextLine();

        Members member = null;
        for (Members m : libraryData.memberCollection) {
            if (m.Email.equals(memberEmail)) {
                member = m;
                break;
            }
        }

        if (member != null) {
            System.out.println("Notification Management for " + member.Name + ":");
            System.out.println("1. Enable notifications");
            System.out.println("2. Disable notifications");
            System.out.println("Enter your choice:");

            String choice = inputScanner.nextLine();

            switch (choice) {
                case "1":
                    member.notificationsEnabled = true;
                    System.out.println("Notifications enabled for " + member.Name);
                    break;
                case "2":
                    member.notificationsEnabled = false;
                    System.out.println("Notifications disabled for " + member.Name);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } else {
            System.out.println("Member not found.");
        }
        System.out.println();
    }

        public static LibraryData libraryData;

        public static void main(String[] args) {
            // Load library data
            libraryData = LibraryData.loadLibraryData();

            // Set up initial book and member collections if data is empty
            if (libraryData.bookCollection.isEmpty()) {
                SetBooks();
            }
            if (libraryData.memberCollection.isEmpty()) {
                SetMembers();
            }

            // Start the overdue fine checker thread
            OverdueFineChecker overdueFineChecker = new OverdueFineChecker();
            overdueFineChecker.start();

            // Start the notification sender thread
            NotificationSender notificationSender = new NotificationSender();
            notificationSender.start();

            // Main loop for the library management system
            while (true){
                System.out.println(" Welcome to the Manga library");
                System.out.println("1. Register Member");
                System.out.println("2. Add Book");
                System.out.println("3. Search Book/Member");
                System.out.println("4. Book Checkout");
                System.out.println("5. Book Return");
                System.out.println("6. Library Catalogue");
                System.out.println("7. Library Members");
                System.out.println("8. Check Due Dates");
                System.out.println("9. View Fines");
                System.out.println("10. Manage Notifications");
                System.out.println("11. Transaction History");
                System.out.println("12. Exit");
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
                        CheckDueDates();
                        break;
                    case "9":
                        ViewFines();
                        break;
                    case "10":
                        ManageNotifications();
                        break;
                    case "11":
                        DisplayTransactions();
                        break;
                    case "12":
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid input, please try again");
                }
                // Save library data when the application closes
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    LibraryData.saveLibraryData(libraryData);
                }));
            }

        }

}
import java.time.LocalDate;
import java.util.List;

public class OverdueFineChecker extends Thread {
    private static final long INTERVAL = 100000;// check fines every minute

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(INTERVAL);
                checkOverdueBooks();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkOverdueBooks() {
        LocalDate currentDate = LocalDate.now();
        System.out.println("Checking for overdue books and updating fines...");
        boolean finesUpdated = false;
        for (Members member : Main.libraryData.memberCollection) {
            double totalFine = 0.0;
            for (String bookTitle : member.borrowedBooks) {
                Books book = getBookByTitle(bookTitle);
                if (book != null) {
                    LocalDate dueDate = member.DueDate;
                    if (currentDate.isAfter(dueDate)) {
                        double fineAmount = Main.calculateOverdueFines(dueDate);
                        totalFine += fineAmount;
                        System.out.println("Member: " + member.Name + ", Book: " + bookTitle + ", Overdue fine: $" + fineAmount);
                        finesUpdated = true;
                    }
                }
            }
            if (totalFine > 0) {
                System.out.println("Total overdue fine for " + member.Name + ": $" + totalFine);
            }
        }
        if (!finesUpdated) {
            System.out.println("No overdue fines to update.");
        }
        System.out.println();
    }

    private Books getBookByTitle(String title) {
        for (Books book : Main.libraryData.bookCollection) {
            if (book.Title.equals(title)) {
                return book;
            }
        }
        return null;
    }
}
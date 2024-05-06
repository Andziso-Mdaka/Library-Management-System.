import java.time.LocalDate;
import java.util.List;

public class NotificationSender extends Thread {
    private static final long INTERVAL = 45000;//  every 20 seconds the notifications will be checked

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(INTERVAL);
                sendNotifications();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendNotifications() {
        LocalDate currentDate = LocalDate.now();
        System.out.println("Sending notifications for due and overdue books...");
        boolean notificationsSent = false;
        for (Members member : Main.libraryData.memberCollection) {
            if (!member.borrowedBooks.isEmpty() && member.notificationsEnabled) {
                StringBuilder notificationMessage = new StringBuilder();
                notificationMessage.append("Dear ").append(member.Name).append(",\n\n");
                notificationMessage.append("Here is the status of your borrowed books:\n\n");

                boolean hasDueOrOverdueBooks = false;
                for (String bookTitle : member.borrowedBooks) {
                    Books book = getBookByTitle(bookTitle);
                    if (book != null) {
                        LocalDate dueDate = member.DueDate;
                        if (currentDate.isAfter(dueDate)) {
                            notificationMessage.append("- ").append(bookTitle).append(" (Overdue)\n");
                            hasDueOrOverdueBooks = true;
                        } else if (currentDate.plusDays(7).isAfter(dueDate)) {
                            notificationMessage.append("- ").append(bookTitle).append(" (Due in ").append(dueDate.until(currentDate).getDays()).append(" days)\n");
                            hasDueOrOverdueBooks = true;
                        } else {
                            notificationMessage.append("- ").append(bookTitle).append("\n");
                        }
                    }
                }

                if (hasDueOrOverdueBooks) {
                    notificationMessage.append("\nThank you for using our library services.\n");
                    System.out.println("Notification sent to " + member.Name + ":\n" + notificationMessage);
                    notificationsSent = true;
                }
            }
        }
        if (!notificationsSent) {
            System.out.println("No notifications to send.");
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
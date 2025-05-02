// Loan.java
import java.util.Date;

/**
 * Representa un préstamo de libro en el sistema
 */
public class Loan {
    private final int id;
    private final int bookId;
    private final int userId;
    private final Date loanDate;
    private Date returnDate;
    private boolean returned;

    public Loan(int id, int bookId, int userId, Date loanDate, Date returnDate, boolean returned) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.returned = returned;
    }

    // Getters y setters
    public int getId() { return id; }
    public int getBookId() { return bookId; }
    public int getUserId() { return userId; }
    public Date getLoanDate() { return loanDate; }
    public Date getReturnDate() { return returnDate; }
    public boolean isReturned() { return returned; }

    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
    public void setReturned(boolean returned) { this.returned = returned; }
}

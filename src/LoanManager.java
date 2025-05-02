// LoanManager.java
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Gestiona el ciclo de vida de los préstamos
 */
public class LoanManager {
    private static LoanManager instance;
    private final List<Loan> loans = new ArrayList<>();
    private final AtomicInteger loanIdCounter = new AtomicInteger(1); // Generador de IDs

    private LoanManager() {}

    public static synchronized LoanManager getInstance() {
        if (instance == null) instance = new LoanManager();
        return instance;
    }

    // Método para generar IDs
    private int generateLoanId() {
        return loanIdCounter.getAndIncrement();
    }

    public void loanBook(int bookId, int userId) throws Exception {
        Book book = BookCatalog.getInstance().findById(bookId);
        User user = UserCatalog.getInstance().findById(userId);

        validateLoan(book, user);

        Loan loan = new Loan(
                generateLoanId(),
                bookId,
                userId,
                new Date(),
                null,
                false
        );

        loans.add(loan);
        book.setAvailable(false);
    }

    private void validateLoan(Book book, User user) throws Exception {
        if (book == null) throw new Exception("Libro no encontrado");
        if (user == null) throw new Exception("Usuario no encontrado");
        if (!book.isAvailable()) throw new Exception("El libro no está disponible");

        long activeLoans = loans.stream()
                .filter(l -> l.getUserId() == user.getId() && !l.isReturned())
                .count();

        if (activeLoans >= user.getLoanLimit()) {
            throw new LoanLimitExceededException("Límite excedido");
        }
    }

    // Método adicional para devolución
    public void returnBook(int bookId) throws Exception {
        Loan loan = loans.stream()
                .filter(l -> l.getBookId() == bookId && !l.isReturned())
                .findFirst()
                .orElseThrow(() -> new Exception("Préstamo no encontrado"));

        loan.setReturned(true);
        loan.setReturnDate(new Date());

        BookCatalog.getInstance().findById(bookId).setAvailable(true);
    }

    public List<Loan> getAllLoans() {
        return new ArrayList<>(loans);
    }
}

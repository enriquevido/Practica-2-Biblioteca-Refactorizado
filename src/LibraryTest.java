import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    private BookCatalog bookCatalog;
    private UserCatalog userCatalog;
    private LoanManager loanManager;

    @BeforeEach
    void setUp() {
        bookCatalog = BookCatalog.getInstance();
        userCatalog = UserCatalog.getInstance();
        loanManager = LoanManager.getInstance();

        // Configurar datos de prueba
        bookCatalog.addBook(new PhysicalBook(1, "1984", "Orwell", 1949, "Dystopian", "A1"));
        userCatalog.addUser(new Student(101, "Alice", "alice@edu", "555-1234"));
    }

    @AfterEach
    void tearDown() {
        // Limpiar estado después de cada prueba
        bookCatalog.getAllBooks().clear();
        userCatalog.getAllUsers().clear();
        loanManager.getAllLoans().clear();
    }

    @Test
    void testSuccessfulLoan() throws Exception {
        loanManager.loanBook(1, 101);
        assertFalse(bookCatalog.findById(1).isAvailable());
    }

    @Test
    void testLoanLimit() {
        // Crear 3 libros adicionales
        for (int i = 2; i <= 4; i++) {
            bookCatalog.addBook(new PhysicalBook(i, "Libro " + i, "Autor", 2023, "Género", "Estante"));
        }

        assertThrows(LoanLimitExceededException.class, () -> {
            for (int i = 1; i <= 4; i++) { // 4 préstamos
                loanManager.loanBook(i, 101); // Libros diferentes
            }
        });
    }
}
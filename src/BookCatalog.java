// BookCatalog.java (Singleton)
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class BookCatalog {
    private static BookCatalog instance;
    private final List<Book> books = new ArrayList<>();

    private BookCatalog() {}

    public static synchronized BookCatalog getInstance() {
        if (instance == null) instance = new BookCatalog();
        return instance;
    }

    public void addBook(Book book) { books.add(book); }

    public Book findById(int id) {
        return books.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Book> searchBooks(Predicate<Book> criteria) {
        return books.stream().filter(criteria).toList();
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books); // Devuelve copia para mantener encapsulamiento
    }
}



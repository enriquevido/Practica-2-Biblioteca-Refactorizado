import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class LibraryApp {
    private final Scanner scanner = new Scanner(System.in);
    private final BookCatalog bookCatalog = BookCatalog.getInstance();
    private final UserCatalog userCatalog = UserCatalog.getInstance();
    private final LoanManager loanManager = LoanManager.getInstance();

    public static void main(String[] args) {
        new LibraryApp().run();
    }

    private void run() {
        initializeSampleData();
        boolean exit = false;

        while (!exit) {
            printMainMenu();
            int option = readOption();

            switch (option) {
                case 1 -> registerBook();
                case 2 -> registrarUsuario();
                case 3 -> loanBook();
                case 4 -> returnBook();
                case 5 -> searchBooks();
                case 6 -> bookCatalog.getAllBooks();
                case 7 -> userCatalog.getAllUsers();
                case 8 -> loanManager.getAllLoans();
                case 9 -> exit = true;
                default -> System.out.println("Opción no válida.");
            }
        }
        System.out.println("¡Sesión finalizada!");
    }

    private void printMainMenu() {
        System.out.println("\n=== SISTEMA DE BIBLIOTECA ===");
        System.out.println("1. Registrar nuevo libro");
        System.out.println("2. Registrar nuevo usuario");
        System.out.println("3. Prestar libro");
        System.out.println("4. Devolver libro");
        System.out.println("5. Buscar libros");
        System.out.println("6. Ver todos los libros");
        System.out.println("7. Ver todos los usuarios");
        System.out.println("8. Ver préstamos activos");
        System.out.println("9. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private int readOption() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void registerBook() {
        System.out.println("--- REGISTRAR LIBRO ---");

        // Selección de tipo
        System.out.println("Tipo de libro:");
        System.out.println("1. Físico");
        System.out.println("2. Digital");
        System.out.print("Seleccione (1-2): ");
        int type = scanner.nextInt();
        scanner.nextLine();  // Limpiar buffer

        // Datos comunes
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Título: ");
        String title = scanner.nextLine();

        System.out.print("Autor: ");
        String author = scanner.nextLine();

        System.out.print("Año: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Género: ");
        String genre = scanner.nextLine();

        // Parámetro adicional según tipo
        String additionalParam = "";
        if (type == 1) {
            System.out.print("Ubicación física (Ej: Estante A2): ");
            additionalParam = scanner.nextLine();
        } else if (type == 2) {
            System.out.print("Formato digital (Ej: PDF, EPUB): ");
            additionalParam = scanner.nextLine();
        }

        // Creación mediante Factory
        Book book = BookFactory.createBook(
                type == 1 ? "PHYSICAL" : "DIGITAL",
                id,
                title,
                author,
                year,
                genre,
                additionalParam
        );

        bookCatalog.addBook(book);
        System.out.println("Libro registrado exitosamente!");
    }

    private void registrarUsuario() {
        System.out.println("\n--- REGISTRAR NUEVO USUARIO ---");

        // Selección de tipo de usuario
        System.out.println("Tipo de usuario:");
        System.out.println("1. Estudiante");
        System.out.println("2. Profesor");
        System.out.print("Seleccione (1-2): ");
        int tipo = scanner.nextInt();
        scanner.nextLine();  // Limpiar buffer

        // Datos comunes
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        // Creación de usuario
        User nuevoUsuario;
        if (tipo == 1) {
            nuevoUsuario = new Student(id, nombre, email, telefono);
        } else if (tipo == 2) {
            nuevoUsuario = new Teacher(id, nombre, email, telefono);
        } else {
            System.out.println("Tipo no válido");
            return;
        }

        // Registrar en el catálogo
        UserCatalog.getInstance().addUser(nuevoUsuario);
        System.out.println("Usuario registrado exitosamente!");
    }

    private void loanBook() {
        System.out.println("\n--- PRESTAR LIBRO ---");
        System.out.print("ID del libro: ");
        int bookId = scanner.nextInt();
        System.out.print("ID del usuario: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        try {
            loanManager.loanBook(bookId, userId); // Pasar ambos IDs
            System.out.println("Préstamo exitoso!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void returnBook() {
        System.out.println("\n--- DEVOLVER LIBRO ---");
        System.out.print("ID del libro: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        try {
            loanManager.returnBook(bookId); // Pasar ID del libro
            System.out.println("Devolución exitosa!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void searchBooks() {
        System.out.println("\n--- BUSCAR LIBROS ---");
        System.out.println("1. Por título");
        System.out.println("2. Por autor");
        System.out.println("3. Por género");
        System.out.print("Seleccione criterio: ");
        int criterio = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        System.out.print("Término de búsqueda: ");
        String termino = scanner.nextLine().toLowerCase();

        Predicate<Book> filtro = switch (criterio) {
            case 1 -> b -> b.getTitle().toLowerCase().contains(termino);
            case 2 -> b -> b.getAuthor().toLowerCase().contains(termino);
            case 3 -> b -> b.getGenre().toLowerCase().contains(termino);
            default -> throw new IllegalArgumentException("Opción no válida");
        };

        List<Book> resultados = bookCatalog.searchBooks(filtro); // Pasar el Predicate
        mostrarResultados(resultados);
    }

    private void mostrarResultados(List<Book> libros) {
        if (libros.isEmpty()) {
            System.out.println("No se encontraron resultados");
            return;
        }
        libros.forEach(b -> System.out.println(
                "ID: " + b.getId() + " | Título: " + b.getTitle() +
                        " | Autor: " + b.getAuthor() + " | Disponible: " + b.isAvailable()
        ));
    }

    private void initializeSampleData() {
        // Datos de ejemplo iniciales
        bookCatalog.addBook(new PhysicalBook(1, "1984", "George Orwell", 1949, "Distopía", "A1"));
        userCatalog.addUser(new Student(101, "Ana López", "ana@uni.com", "555-1234"));
    }
}

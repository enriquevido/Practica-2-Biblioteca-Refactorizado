public class BookFactory {
    public static Book createBook(String type, int id, String title, String author,
                                  int year, String genre, String additionalParam) {
        switch (type.toUpperCase()) {
            case "PHYSICAL":
                return new PhysicalBook(id, title, author, year, genre, additionalParam);
            case "DIGITAL":
                return new DigitalBook(id, title, author, year, genre, additionalParam);
            default:
                throw new IllegalArgumentException("Tipo de libro no válido");
        }
    }
}

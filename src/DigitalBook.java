// DigitalBook.java
public class DigitalBook extends Book {
    private final String format;

    public DigitalBook(int id, String title, String author, int year, String genre, String format) {
        super(id, title, author, year, genre);
        this.format = format;
    }

    @Override
    public String getType() { return "Digital"; }
    public String getFormat() { return format; }
}

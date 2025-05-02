// PhysicalBook.java
public class PhysicalBook extends Book {
    private final String location;

    public PhysicalBook(int id, String title, String author, int year, String genre, String location) {
        super(id, title, author, year, genre);
        this.location = location;
    }

    @Override
    public String getType() { return "Physical"; }
    public String getLocation() { return location; }
}

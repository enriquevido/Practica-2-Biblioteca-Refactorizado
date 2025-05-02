// Teacher.java
public class Teacher extends User {
    public Teacher(int id, String name, String email, String phone) {
        super(id, name, email, phone);
    }

    @Override
    public int getLoanLimit() { return 5; }
}

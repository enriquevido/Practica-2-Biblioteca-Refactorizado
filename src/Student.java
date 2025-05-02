// Student.java
public class Student extends User {
    public Student(int id, String name, String email, String phone) {
        super(id, name, email, phone);
    }

    @Override
    public int getLoanLimit() { return 3; }
}

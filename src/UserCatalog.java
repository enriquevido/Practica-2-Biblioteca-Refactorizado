// UserCatalog.java
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Catálogo Singleton que gestiona la colección de usuarios.
 * Implementa operaciones básicas de registro y búsqueda de usuarios.
 *
 * @author TuNombre
 */
public class UserCatalog {
    private static UserCatalog instance;
    private final List<User> users = new ArrayList<>();

    // Constructor privado para Singleton
    private UserCatalog() {}

    /**
     * Obtiene la instancia única del catálogo de usuarios
     * @return Instancia Singleton de UserCatalog
     */
    public static synchronized UserCatalog getInstance() {
        if (instance == null) {
            instance = new UserCatalog();
        }
        return instance;
    }

    /**
     * Registra un nuevo usuario en el sistema
     * @param user Usuario a registrar
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Busca un usuario por su ID único
     * @param id Identificador del usuario
     * @return Usuario encontrado o null si no existe
     */
    public User findById(int id) {
        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca usuarios que cumplan con un criterio específico
     * @param criteria Predicado para filtrar usuarios
     * @return Lista de usuarios que cumplen el criterio
     */
    public List<User> searchUsers(Predicate<User> criteria) {
        return users.stream()
                .filter(criteria)
                .toList();
    }

    /**
     * Obtiene todos los usuarios registrados
     * @return Lista completa de usuarios
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}

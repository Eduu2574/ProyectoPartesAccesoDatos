package ribera.practicapartes.Models;

public class SessionManager {
    private static SessionManager instance; // Instancia única
    private Profesor profesorAutenticado;     // Usuario autenticado

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setProfesorAutenticado(Profesor profesor) {
        this.profesorAutenticado = profesor;
    }

    public Profesor getProfesorAutenticado() {
        return profesorAutenticado;
    }

    // Método para cerrar sesión
    public void cerrarSesion() {
        this.profesorAutenticado = null;
    }
}


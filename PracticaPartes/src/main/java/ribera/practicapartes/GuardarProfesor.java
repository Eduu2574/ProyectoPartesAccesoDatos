package ribera.practicapartes;

import ribera.practicapartes.Models.Profesor;

public class GuardarProfesor {
    private static Profesor profesor_guardado;

    public static Profesor getProfesor() {
        return profesor_guardado;
    }

    public static void setProfesor(Profesor profesor) {
        profesor_guardado = profesor;
    }
}
package ribera.practicapartes.Utils;

/**
 * La clase {@code Validador} proporciona métodos estáticos para validar campos de texto y formatos específicos
 * en la aplicación.
 */
public class Validador {

    /**
     * Constructor predeterminado para la clase {@code Validador}.
     * Privado para evitar que su instancia, ya que solo contiene métodos estáticos.
     */
    private Validador() {}

    /**
     * Valida que un campo de texto no esté vacío.
     *
     * @param texto el texto a validar.
     * @return {@code true} si el texto no es {@code null} y no está vacío (después de eliminar espacios en blanco);
     *         {@code false} en caso contrario.
     */
    public static boolean validarTextoNoVacio(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
}
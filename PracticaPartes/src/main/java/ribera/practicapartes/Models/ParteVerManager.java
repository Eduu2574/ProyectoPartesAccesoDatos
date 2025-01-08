package ribera.practicapartes.Models;

public class ParteVerManager {
    private static ParteVerManager instance; // Instancia única
    private ParteIncidencia parteSeleccionado;     // Parte Seleccionado

    private ParteVerManager() {}

    public static ParteVerManager getInstance() {
        if (instance == null) {
            instance = new ParteVerManager();
        }
        return instance;
    }

    public void setParteSeleccionado(ParteIncidencia parte) {
        this.parteSeleccionado = parte;
    }

    public ParteIncidencia getParteSeleccionado() {
        return parteSeleccionado;
    }
}

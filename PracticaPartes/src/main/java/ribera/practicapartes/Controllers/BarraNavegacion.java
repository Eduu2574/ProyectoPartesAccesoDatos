package ribera.practicapartes.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ribera.practicapartes.Models.SessionManager;
import ribera.practicapartes.Models.TipoProfesor;

import java.io.IOException;

import static ribera.practicapartes.Utils.AlertUtils.mostrarError;
import static ribera.practicapartes.Utils.CambioEscena.cambiarEscena;

public class BarraNavegacion {

    @FXML
    private Button btnCrearParte;
    @FXML
    private Button btnListaPartes;
    @FXML
    private Button btnListaAlumnos;
    @FXML
    private Button btnCrearProfesor;

    public BarraNavegacion() {}

    @FXML
    public void initialize() {
        if (!SessionManager.getInstance().getProfesorAutenticado().getTipo().equals(TipoProfesor.jefe_de_estudios)) {
            btnCrearProfesor.setVisible(false);
            btnListaAlumnos.setVisible(false);
            btnListaPartes.setVisible(false);
        }
        btnCrearParte.setOnAction(_ -> {
            try {
                cambiarEscena(btnCrearParte, "parteVerde.fxml");
            } catch (IOException e) {
                mostrarError("Error al cambiar de pestaña.");
            }
        });
        btnListaPartes.setOnAction(_ -> {
            try {
                cambiarEscena(btnListaPartes, "listaPartes.fxml");

            } catch (IOException e) {
                mostrarError("Error al cambiar de pestaña.");
            }
        });
        btnListaAlumnos.setOnAction(_ -> {
            try {
                cambiarEscena(btnListaAlumnos, "listaAlumnos.fxml");
            } catch (IOException e) {
                e.printStackTrace();
                mostrarError("Error al cambiar de pestaña.");
            }
        });
        btnCrearProfesor.setOnAction(_ -> {
            try {
                cambiarEscena(btnCrearProfesor, "crearProfesor.fxml");
            } catch (IOException e) {
                mostrarError("Error al cambiar de pestaña.");
            }
        });
    }
}

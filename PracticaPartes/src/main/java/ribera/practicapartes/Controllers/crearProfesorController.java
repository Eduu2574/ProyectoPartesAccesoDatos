package ribera.practicapartes.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ribera.practicapartes.DAO.ProfesorDAO;
import ribera.practicapartes.Models.Profesor;
import ribera.practicapartes.Models.TipoProfesor;
import ribera.practicapartes.Utils.AlertUtils;

public class crearProfesorController {

    @FXML
    private TextField tfNombre;

    @FXML
    private PasswordField pfContrasena;

    @FXML
    private TextField tfNumeroAsignado;

    @FXML
    private ComboBox<TipoProfesor> cbTipo;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnCancelar;

    private final ProfesorDAO profesorDAO = new ProfesorDAO();

    @FXML
    void onCrearClick(ActionEvent event) {
        // Validar que los campos no estén vacíos
        if (tfNombre.getText().isEmpty() || pfContrasena.getText().isEmpty() || tfNumeroAsignado.getText().isEmpty() || cbTipo.getValue() == null) {
            AlertUtils.mostrarError("Debes completar todos los campos.");
            return;
        }

        // Crear el objeto Profesor
        Profesor profesor = new Profesor(
                0, // El ID se genera automáticamente
                pfContrasena.getText(),
                tfNombre.getText(),
                tfNumeroAsignado.getText(),
                cbTipo.getValue()
        );

        // Guardar el profesor en la base de datos
        if (profesorDAO.crearProfesor(profesor)) {
            AlertUtils.mostrarAviso("El profesor ha sido creado correctamente.");
        } else {
            AlertUtils.mostrarError("Hubo un error al crear el profesor. Intenta nuevamente.");
        }
    }

    @FXML
    void onCancelarClick(ActionEvent event) {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        cbTipo.getItems().setAll(TipoProfesor.values());
    }
}

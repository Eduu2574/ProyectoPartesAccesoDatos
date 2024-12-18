package ribera.practicapartes.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import ribera.practicapartes.DAO.AlumnosDAO;
import ribera.practicapartes.DAO.PartesDAO;
import ribera.practicapartes.GuardarParte;
import ribera.practicapartes.Models.*;
import ribera.practicapartes.Utils.AlertUtils;
import ribera.practicapartes.Utils.CambioEscena;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ParteNaranjaController implements Initializable {

    @FXML
    private Button BtActualizar;

    @FXML
    private Button BtCrear;

    @FXML
    private TextArea TaDescripcion;

    @FXML
    private TextArea TaSancion;

    @FXML
    private Button bt_parteNaranja;

    @FXML
    private Button bt_parteRojo;

    @FXML
    private Button bt_parteVerde;

    @FXML
    private ComboBox<String> cbHora;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private Label tfGrupo;

    @FXML
    private Label tfNombreProfesor;

    @FXML
    private TextField tfNumExpediente;


    private PartesDAO partes = new PartesDAO();
    private AlumnosDAO alumnosDAO = new AlumnosDAO();
    private Alumno alumno;
    private Profesor profesor = SessionManager.getInstance().getProfesorAutenticado();

    @FXML
    void onActualizarParteClick(ActionEvent event) {
        ParteIncidencia parte = GuardarParte.getParte();
        if (parte != null) {
            // Actualizamos los datos del parte
            parte.setFecha(dpFecha.getValue().toString());
            parte.setHora(cbHora.getValue());
            parte.setDescripcion(TaDescripcion.getText());
            parte.setSancion(TaSancion.getText());
            parte.setColor(ColorParte.NARANJA); // Especifica que es un parte naranja
            parte.setPuntos_parte(parte.getColor().getPuntos());

            alumno = parte.getAlumno();
            alumnosDAO.actualizarPuntosAlumno(alumno, parte); // Actualiza los puntos del alumno

            if (partes.actualizarParte(parte)) {
                AlertUtils.mostrarAviso("El parte naranja se ha actualizado correctamente.");
                // Cierra la ventana actual
                Stage stage = (Stage) BtActualizar.getScene().getWindow();
                stage.close();
            } else {
                AlertUtils.mostrarError("No se ha podido actualizar el parte naranja.");
            }
        }
    }


    @FXML
    void onCrearParteClick(ActionEvent event) {
        if (validarCampos()) {
            ParteIncidencia parte = crearParte();
            //alumnosDAO.actualizarPuntosAlumno(alumno, parte); // Actualización de puntos del alumno
            if (partes.crearParte(parte)) {
                AlertUtils.mostrarAviso("El parte ha sido creado correctamente.");
                limpiarCampos();
            } else {
                AlertUtils.mostrarError("Error, no se ha podido crear el parte, revisa los datos");
            }
        }
    }

    @FXML
    void onParteNaranjaClick(ActionEvent event) throws IOException {

    }

    @FXML
    void onParteRojoClick(ActionEvent event) throws IOException {
        // Cambiar a la escena de Parte Rojo
        CambioEscena.cambiarEscena(bt_parteRojo, "parteRojo.fxml");
    }

    @FXML
    void onParteVerdeClick(ActionEvent event) throws IOException {
        // Cambiar a la escena de Parte Verde con los datos del controlador
        CambioEscena.cambiarEscena(bt_parteVerde, "parteVerde.fxml");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializarProfesor();
        inicializarComboBoxHora();
        cargarParteExistente();
    }

    @FXML
    void onPressedAlumno(KeyEvent event) {
        String numExpediente = tfNumExpediente.getText();

        if (!numExpediente.isEmpty()) {
            alumno = alumnosDAO.buscarAlumnoPorExpediente(numExpediente);
            if (alumno != null) {
                tfGrupo.setText(alumno.getGrupo().getNombreGrupo());
            } else {
                tfGrupo.setText(null);
            }
        } else {
            tfGrupo.setText("");
        }
    }

    private void limpiarCampos() {
        tfNumExpediente.clear();
        tfGrupo.setText("");
        dpFecha.setValue(null);
        cbHora.setValue(null);
        TaDescripcion.clear();
        TaSancion.setText("");
    }

    private void inicializarProfesor() {
        if (profesor != null) {
            tfNombreProfesor.setText(profesor.getNombre());
        }
    }

    private void inicializarComboBoxHora() {
        cbHora.getItems().addAll(
                "08:30-09:20", "09:25-10:15", "10:20-11:10", "11:40-12:30", "12:35-13:25", "13:30-14:20", "16:00-16:50", "16:55-17:45", "17:50-18:40", "18:55-19:45", "19:50-20:40", "20:45-21:40");
    }

    private void cargarParteExistente() {
        if (GuardarParte.getParte() != null) {
            tfNumExpediente.setText(GuardarParte.getParte().getAlumno().getNumero_expediente());
            tfGrupo.setText(GuardarParte.getParte().getGrupo().getNombreGrupo());
            dpFecha.setValue(LocalDate.parse(GuardarParte.getParte().getFecha()));
            cbHora.setValue(GuardarParte.getParte().getHora());
            TaDescripcion.setText(GuardarParte.getParte().getDescripcion());
            TaSancion.setText(GuardarParte.getParte().getSancion());
        }
    }

    private boolean validarCampos() {
        if (tfNumExpediente.getText().isEmpty() || dpFecha.getValue() == null || TaDescripcion.getText().isEmpty() || cbHora.getValue() == null || tfGrupo.getText() == null) {
            AlertUtils.mostrarError("Introduce todos los datos.");
            return false;
        }
        return true;
    }

    private ParteIncidencia crearParte() {
        return new ParteIncidencia(
                alumno,
                profesor.getId_profesor(),
                alumno.getGrupo(),
                dpFecha.getValue().toString(),
                cbHora.getValue(),
                TaDescripcion.getText(),
                TaSancion.getText(),
                ColorParte.NARANJA
        );
    }
}

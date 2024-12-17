package ribera.practicapartes.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import ribera.practicapartes.GuardarProfesor;
import ribera.practicapartes.Models.*;
import ribera.practicapartes.Utils.AlertUtils;
import ribera.practicapartes.DAO.AlumnosDAO;
import ribera.practicapartes.DAO.PartesDAO;
import ribera.practicapartes.GuardarParte;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import ribera.practicapartes.Utils.CambioEscena;
import ribera.practicapartes.Utils.R;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ParteVerdeController implements Initializable {

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
            parte.setColor(ColorParte.VERDE); // Especifica que es un parte verde
            parte.setPuntos_parte(parte.getColor().getPuntos());

            alumno = parte.getAlumno();
            alumnosDAO.actualizarPuntosAlumno(alumno, parte); // Actualiza los puntos del alumno

            if (partes.actualizarParte(parte)) {
                AlertUtils.mostrarAviso("El parte verde se ha actualizado correctamente.");
                // Cierra la ventana actual
                Stage stage = (Stage) BtActualizar.getScene().getWindow();
                stage.close();
            } else {
                AlertUtils.mostrarError("No se ha podido actualizar el parte verde.");
            }
        }
    }



    @FXML
    void onCrearParteClick(ActionEvent event) {
        if (validarCampos()) {
            ParteIncidencia parte = crearParte(ColorParte.VERDE);
            alumnosDAO.actualizarPuntosAlumno(alumno, parte);

            if (partes.crearParte(parte)) {
                AlertUtils.mostrarAviso("El parte ha sido creado correctamente.");
                limpiarCampos();
            } else {
                AlertUtils.mostrarError("No se ha podido crear el parte, revisa los datos.");
            }
        } else {
            AlertUtils.mostrarError("Introduce todos los datos.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializarCampos();
        cargarParteExistente();
    }

    @FXML
    void onParteNaranjaClick(ActionEvent event) throws IOException {
        // Llamar al método de cambio de escena
        CambioEscena.cambiarEscena(bt_parteNaranja, "parteNaranja.fxml");
    }


    @FXML
    void onParteRojoClick(ActionEvent event) throws IOException {
        // Cambiar a la escena de Parte Rojo
        CambioEscena.cambiarEscena(bt_parteRojo, "parteRojo.fxml");
    }

    @FXML
    void onParteVerdeClick(ActionEvent event) {

    }

    @FXML
    void onPressedAlumno(KeyEvent event) {
        String numExpediente = tfNumExpediente.getText();
        alumno = alumnosDAO.buscarAlumnoPorExpediente(numExpediente);

        if (numExpediente.isEmpty()) {
            tfGrupo.setText("");
        } else {
            tfGrupo.setText(alumno != null ? alumno.getGrupo().getNombreGrupo() : null);
        }
    }

    private void inicializarCampos() {
        tfNombreProfesor.setText(profesor.getNombre());
        cbHora.getItems().addAll(
                "08:30-09:20", "09:25-10:15", "10:20-11:10", "11:40-12:30", "12:35-13:25", "13:30-14:20",
                "16:00-16:50", "16:55-17:45", "17:50-18:40", "18:55-19:45", "19:50-20:40", "20:45-21:40");
    }

    private void cargarParteExistente() {
        ParteIncidencia parte = GuardarParte.getParte();
        if (parte != null) {
            tfNumExpediente.setText(parte.getAlumno().getNumero_expediente());
            tfGrupo.setText(parte.getGrupo().getNombreGrupo());
            dpFecha.setValue(LocalDate.parse(parte.getFecha()));
            cbHora.setValue(parte.getHora());
            TaDescripcion.setText(parte.getDescripcion());
            TaSancion.setText(parte.getSancion());
        }
    }

    private boolean validarCampos() {
        return !tfNumExpediente.getText().isEmpty()&& dpFecha.getValue() != null&& !TaDescripcion.getText().isEmpty()&& cbHora.getValue() != null&& !TaSancion.getText().isEmpty() && !(tfGrupo.getText() == null);    }

    private ParteIncidencia crearParte(ColorParte color) {
        return new ParteIncidencia(
                alumno, profesor.getId_profesor(), alumno.getGrupo(),
                dpFecha.getValue().toString(), cbHora.getValue(),
                TaDescripcion.getText(), TaSancion.getText(), color
        );
    }

    private void actualizarDatosParte(ParteIncidencia parte) {
        parte.setFecha(dpFecha.getValue().toString());
        parte.setHora(cbHora.getValue());
        parte.setDescripcion(TaDescripcion.getText());
        parte.setSancion(TaSancion.getText());
        parte.setColor(ColorParte.VERDE);
        parte.setPuntos_parte(parte.getColor().getPuntos());
    }

    private void limpiarCampos() {
        tfNumExpediente.clear();
        tfGrupo.setText("");
        dpFecha.setValue(null);
        cbHora.setValue(null);
        TaDescripcion.clear();
        TaSancion.clear();
    }
}

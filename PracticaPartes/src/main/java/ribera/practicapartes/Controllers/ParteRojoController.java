package ribera.practicapartes.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import ribera.practicapartes.DAO.AlumnosDAO;
import ribera.practicapartes.DAO.PartesDAO;
import ribera.practicapartes.GuardarParte;
import ribera.practicapartes.GuardarProfesor;
import ribera.practicapartes.Models.Alumno;
import ribera.practicapartes.Models.ColorParte;
import ribera.practicapartes.Models.ParteIncidencia;
import ribera.practicapartes.Models.Profesor;
import ribera.practicapartes.Utils.AlertUtils;
import ribera.practicapartes.Utils.CambioEscena;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ParteRojoController implements Initializable {

    @FXML
    private Button BtActualizar;

    @FXML
    private Button BtCrear;

    @FXML
    private ComboBox<String> CbSancion;

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
    private Profesor profesor;

    public ParteRojoController(Profesor profesor) {
        this.profesor = profesor;
    }

    @FXML
    void onActualizarParteClick(ActionEvent event) {
        // Verificamos si los campos necesarios están completos
        if (tfNumExpediente.getText().isEmpty() || dpFecha.getValue() == null || TaDescripcion.getText().isEmpty() || cbHora.getValue() == null || CbSancion.getValue().isEmpty()) {
            AlertUtils.mostrarError("Introduce todos los datos.");
            if (CbSancion.getValue().equals("Otro:")) {
                if (TaSancion.getText().isEmpty()) {
                    AlertUtils.mostrarError("Tienes que rellenar todos los campos.");
                }
            }
        } else {
            // Si la sanción es "Otro:", usamos el valor de TaSancion
            String sancion = CbSancion.getValue().equals("Otro:") ? TaSancion.getText() : CbSancion.getValue();

            // Crear un nuevo objeto ParteIncidencia con los datos actualizados
            ParteIncidencia parte = new ParteIncidencia(
                    alumno,
                    profesor.getId_profesor(), alumno.getGrupo(),
                    dpFecha.getValue().toString(),
                    cbHora.getValue(),
                    TaDescripcion.getText(),
                    sancion,
                    ColorParte.ROJO
            );

            // Establecer el ID del parte desde el parte cargado si ya existe
            if (GuardarParte.getParte() != null) {
                parte.setId_parte((GuardarParte.getParte().getId_parte()));
            }

            // Llamar al método de actualización del DAO
            PartesDAO partesDAO = new PartesDAO();
            boolean actualizacionExitosa = partesDAO.actualizarParte(parte);

            if (actualizacionExitosa) {
                AlertUtils.mostrarAviso("El parte ha sido actualizado correctamente.");
            } else {
                AlertUtils.mostrarError("No se ha podido actualizar el parte. Intenta de nuevo.");
            }
        }
    }


    @FXML
    void onCrearParteClick(ActionEvent event) {
        if (!validarCampos()) return;
        String sancion = obtenerSancion();

        ParteIncidencia parte = new ParteIncidencia(alumno, profesor.getId_profesor(), alumno.getGrupo(), dpFecha.getValue().toString(), cbHora.getValue(), TaDescripcion.getText(), sancion, ColorParte.ROJO);

        if (partes.crearParte(parte)) {
            AlertUtils.mostrarAviso("El parte ha sido creado correctamente.");
            limpiarCampos();
        } else {
            AlertUtils.mostrarError("No se ha podido crear el parte, revisa los datos.");
        }
    }

    @FXML
    void onParteNaranjaClick(ActionEvent event) throws IOException {
        CambioEscena.cambiarEscenaConDatos(bt_parteNaranja, "parteNaranja.fxml", new ParteNaranjaController(profesor));
    }

    @FXML
    void onParteRojoClick(ActionEvent event) throws IOException {
        CambioEscena.cambiarEscenaConDatos(bt_parteRojo, "parteRojo.fxml", new ParteRojoController(profesor));
    }

    @FXML
    void onParteVerdeClick(ActionEvent event) throws IOException {
        CambioEscena.cambiarEscenaConDatos(bt_parteVerde, "parteVerde.fxml", new ParteVerdeController(profesor));
    }

    @FXML
    void onSancionRecibirClick(ActionEvent event) {
        TaSancion.setVisible("Otro:".equals(CbSancion.getValue()));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TaSancion.setVisible(false);
        cbHora.getItems().addAll(
                "08:30-09:20", "09:25-10:15", "10:20-11:10",
                "11:40-12:30", "12:35-13:25", "13:30-14:20"
        );
        CbSancion.getItems().addAll(List.of(
                "Incoacción de expediente o en su caso expediente abreviado",
                "Reunión con la Comisión de Convivencia",
                "Es obligatorio pedir disculpas a la persona/as contra las que ejercio daño físico o moral, y/o reparar los daños materiales causados",
                "Otro:"
        ));
        tfNombreProfesor.setText(profesor.getNombre());
        cargarDatosExistentes();
    }

    private void cargarDatosExistentes() {
        if (GuardarParte.getParte() != null) {
            tfNumExpediente.setText(GuardarParte.getParte().getAlumno().getNumero_expediente());
            tfGrupo.setText(GuardarParte.getParte().getGrupo().getNombreGrupo());
            dpFecha.setValue(LocalDate.parse(GuardarParte.getParte().getFecha()));
            cbHora.setValue(GuardarParte.getParte().getHora());
            TaDescripcion.setText(GuardarParte.getParte().getDescripcion());
        }
    }

    private boolean validarCampos() {
        if (tfNumExpediente.getText().isEmpty() || dpFecha.getValue() == null || TaDescripcion.getText().isEmpty() || cbHora.getValue() == null || CbSancion.getValue() == null ||tfGrupo.getText()==null) {
            AlertUtils.mostrarError("Introduce todos los datos.");
            return false;
        }
        if (CbSancion.getValue().equals("Otro:") && TaSancion.getText().isEmpty()) {
            AlertUtils.mostrarError("Debes rellenar el campo de sanción personalizada.");
            return false;
        }
        return true;
    }

    private String obtenerSancion() {
        return "Otro:".equals(CbSancion.getValue()) ? TaSancion.getText() : CbSancion.getValue();
    }

    private void limpiarCampos() {
        tfNumExpediente.clear();
        tfGrupo.setText("");
        dpFecha.setValue(null);
        cbHora.setValue(null);
        TaDescripcion.clear();
        TaSancion.clear();
        CbSancion.setValue(null);
        TaSancion.setVisible(false);
    }
}

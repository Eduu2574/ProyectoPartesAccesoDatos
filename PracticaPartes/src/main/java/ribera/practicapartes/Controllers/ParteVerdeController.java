package ribera.practicapartes.Controllers;

import ribera.practicapartes.Alerta;
import ribera.practicapartes.Models.Profesor;
import ribera.practicapartes.Utils.CambioEscena;
import ribera.practicapartes.DAO.AlumnosDAO;
import ribera.practicapartes.DAO.PartesDAO;
import ribera.practicapartes.GuardarParte;
import ribera.practicapartes.GuardarProfesor;
import ribera.practicapartes.Models.Alumno;
import ribera.practicapartes.Models.ColorParte;
import ribera.practicapartes.Models.ParteIncidencia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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


    private PartesDAO partes=new PartesDAO();
    private AlumnosDAO alumnosDAO=new AlumnosDAO();
    private Alumno alumno;
    private Profesor profesor;

    public ParteVerdeController(Profesor profesor) {
        this.profesor=profesor;
    }


    @FXML
    void onActualizarParteClick(ActionEvent event) {

    }

    @FXML
    void onCrearParteClick(ActionEvent event) {
        if (tfNumExpediente.getText().isEmpty() || dpFecha.getValue() == null || TaDescripcion.getText().isEmpty() || cbHora.getValue().isEmpty() || TaSancion.getText().isEmpty()) {
            Alerta.Error("Introduce todos los datos");
        } else {
            ParteIncidencia parte = new ParteIncidencia(alumno, GuardarProfesor.getProfesor(), alumno.getGrupo(), dpFecha.getValue().toString(), cbHora.getValue(), TaDescripcion.getText(), TaSancion.getText(), ColorParte.VERDE);
            alumnosDAO.actualizarPuntosAlumno(alumno, parte);
            partes.crearParte(parte);
            Alerta.Info("El parte ha sido creado correctamente.");
        }
    }

    @FXML
    void onParteNaranjaClick(ActionEvent event) {
        CambioEscena.cambiarEscena(bt_parteNaranja, "parteNaranja.fxml");

    }

    @FXML
    void onParteRojoClick(ActionEvent event) {
        CambioEscena.cambiarEscena(bt_parteRojo, "parteRojo.fxml");

    }

    @FXML
    void onParteVerdeClick(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbHora.getItems().addAll(
                "08:30-09:20",
                "09:25-10:15",
                "10:20-11:10",
                "11:40-12:30",
                "12:35-13:25",
                "13:30-14:20"
        );

        tfNombreProfesor.setText(profesor.getNombre());


        if(GuardarParte.getParte() != null){
            tfNumExpediente.setText(GuardarParte.getParte().getAlumno().getNumero_expediente());
            tfGrupo.setText(GuardarParte.getParte().getGrupo().getNombreGrupo());
            dpFecha.setValue(LocalDate.parse(GuardarParte.getParte().getFecha()));
            cbHora.setValue(GuardarParte.getParte().getHora());
            TaDescripcion.setText(GuardarParte.getParte().getDescripcion());
            TaSancion.setText(GuardarParte.getParte().getSancion());
        }
    }
}

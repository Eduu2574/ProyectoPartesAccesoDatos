package ribera.practicapartes.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ribera.practicapartes.DAO.PartesDAO;
import ribera.practicapartes.Models.ColorParte;
import ribera.practicapartes.Models.ParteIncidencia;
import ribera.practicapartes.Models.ParteVerManager;
import ribera.practicapartes.Utils.CambioEscena;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class verParteController {
    @FXML
    public TextArea descripcion;
    @FXML
    public TextField profesor;
    @FXML
    public TextField alumno;
    @FXML
    public DatePicker fecha;
    @FXML
    public ComboBox<String> hora;
    @FXML
    public ComboBox<ColorParte> color;
    @FXML
    public ComboBox<String> sanciones;
    @FXML
    public TextArea sancion;
    @FXML
    public Label titulo;
    @FXML
    public Button btnActualizar;
    @FXML
    public Button btnSalir;

    private ParteIncidencia parte;
    DateTimeFormatter formateadorFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private String valorHora;
    private ColorParte valorColor;
    private String valorSancion;

    public void initialize () {
        parte = ParteVerManager.getInstance().getParteSeleccionado();

        hora.getItems().addAll(
                "08:30-09:20", "09:25-10:15", "10:20-11:10", "11:40-12:30", "12:35-13:25", "13:30-14:20",
                "16:00-16:50", "16:55-17:45", "17:50-18:40", "18:55-19:45", "19:50-20:40", "20:45-21:40");
        color.getItems().addAll(ColorParte.VERDE, ColorParte.NARANJA, ColorParte.ROJO);
        sanciones.getItems().addAll("Incoacción de expediente o en su caso expediente abreviado",
                "Reunión con la Comisión de Convivencia",
                "Es obligatorio pedir disculpas a la persona/as contra las que ejercio daño físico o moral, y/o reparar los daños materiales causados",
                "Otro:");
        titulo.setText(titulo.getText() + " " + parte.getId_parte());

        descripcion.setText(parte.getDescripcion());
        profesor.setText(String.valueOf(parte.getId_profesor()));
        alumno.setText(parte.getAlumno().getNombre_alum());
        fecha.setValue(LocalDate.parse(parte.getFecha(), formateadorFecha));
        hora.setValue(parte.getHora());
        valorHora = parte.getHora();
        color.setValue(parte.getColor());
        valorColor = parte.getColor();

        if (valorColor != ColorParte.ROJO) {
            sanciones.setVisible(false);
            valorSancion = parte.getSancion();
            sancion.setText(valorSancion);
        } else {
            sanciones.setVisible(true);
            sancion.setVisible(false);
            switch (parte.getSancion()){
                case "Incoacción de expediente o en su caso expediente abreviado":
                    valorSancion = parte.getSancion();
                    sanciones.getSelectionModel().select("Incoacción de expediente o en su caso expediente abreviado");
                    break;
                case "Reunión con la Comisión de Convivencia":
                    valorSancion = parte.getSancion();
                    sanciones.getSelectionModel().select("Reunión con la Comisión de Convivencia");
                    break;
                case "Es obligatorio pedir disculpas a la persona/as contra las que ejercio daño físico o moral, y/o reparar los daños materiales causados":
                    valorSancion = parte.getSancion();
                    sanciones.getSelectionModel().select("Es obligatorio pedir disculpas a la persona/as contra las que ejercio daño físico o moral, y/o reparar los daños materiales causados");
                    break;
                default:
                    sanciones.getSelectionModel().select("Otro:");
                    sancion.setVisible(true);
                    valorSancion = parte.getSancion();
                    sancion.setText(valorSancion);
                    break;
            }
        }
    }


    public void cambioColor(ActionEvent event) {
        valorColor = color.getValue();
        if (valorColor != ColorParte.ROJO) {
            sanciones.setVisible(false);
            sancion.setVisible(true);
        } else {
            sanciones.setVisible(true);
            sancion.setVisible(false);

            sanciones.getSelectionModel().select("Incoacción de expediente o en su caso expediente abreviado");
            valorSancion = "Incoacción de expediente o en su caso expediente abreviado";
        }
    }

    public void cambioSancion(ActionEvent event) {
        if (sanciones.getSelectionModel().getSelectedItem().equals("Otro:")) {
            sancion.setVisible(true);
        } else {
            sancion.setVisible(false);

            switch (sanciones.getSelectionModel().getSelectedItem()){
                case "Incoacción de expediente o en su caso expediente abreviado":
                    valorSancion = "Incoacción de expediente o en su caso expediente abreviado";
                    break;
                case "Reunión con la Comisión de Convivencia":
                    valorSancion = "Reunión con la Comisión de Convivencia";
                    break;
                case "Es obligatorio pedir disculpas a la persona/as contra las que ejercio daño físico o moral, y/o reparar los daños materiales causados":
                    valorSancion = "Es obligatorio pedir disculpas a la persona/as contra las que ejercio daño físico o moral, y/o reparar los daños materiales causados";
                    break;
                default:
                    sanciones.getSelectionModel().select("Otro:");
                    sancion.setVisible(true);
                    break;
            }
        }
    }

    public void onActualizar(ActionEvent event) throws IOException {
        if (!sancion.getText().isEmpty()) {
            valorSancion = sancion.getText();
        }

        parte.setFecha(fecha.getValue().toString());
        parte.setHora(hora.getValue().toString());
        parte.setDescripcion(descripcion.getText());
        parte.setColor(valorColor);
        parte.setSancion(valorSancion);

        PartesDAO a = new PartesDAO();
        a.actualizarParte(parte);

        CambioEscena.cambiarEscena(btnActualizar, "listaPartes.fxml");
    }

    public void onSalir(ActionEvent event) throws IOException {
        CambioEscena.cambiarEscena(btnSalir, "listaPartes.fxml");
    }
}

package ribera.practicapartes.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;
import ribera.practicapartes.DAO.ProfesorDAO;
import ribera.practicapartes.Models.Profesor;
import ribera.practicapartes.Utils.R;

import java.io.IOException;
import java.util.Objects;

import static ribera.practicapartes.Utils.AlertUtils.mostrarConfirmacion;
import static ribera.practicapartes.Utils.AlertUtils.mostrarError;
import static ribera.practicapartes.Utils.Validador.validarTextoNoVacio;


public class LoginController {
    ProfesorDAO dao = new ProfesorDAO();
    private Profesor profesor;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtId;

    @FXML
    private PasswordField txtPassword;

    @FXML
    void onLoginClick(ActionEvent event) throws IOException {
        String numeroid = txtId.getText();
        String pswd = DigestUtils.sha256Hex(txtPassword.getText());

        if (!validarTextoNoVacio(numeroid)) {
            mostrarError("El campo \"Número de Identificación\" no puede estar vacio.");
            return;
        }
        if(!validarTextoNoVacio(txtPassword.getText())) {
            mostrarError("El campo \"Contraseña\" no puede estar vacio.");
            return;
        }

        if (dao.authenticate(numeroid, pswd)) {
            profesor = dao.obtenerProfesor(numeroid);
            logIn(numeroid);
        } else {
            mostrarError("Los datos de inicio de sesión no son correctos.");
        }
    }

    void logIn(String numeroid) throws IOException {
        mostrarConfirmacion("Login correcto.");
        ParteVerdeController parteController = new ParteVerdeController(profesor);
        FXMLLoader loader = new FXMLLoader(R.getUI("parteVerde.fxml"));
        loader.setController(parteController);
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();

        try {
            Image icono = new Image(Objects.requireNonNull(R.getImage("logo_icono.jpg")));
            stage.getIcons().add(icono);
        } catch (Exception e) {
            System.out.println("No se ha podido cargar el icono de la aplicación.");
        }

        stage.setScene(scene);
        stage.show();
    }

}
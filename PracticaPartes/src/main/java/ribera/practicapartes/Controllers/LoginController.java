package ribera.practicapartes.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.commons.codec.digest.DigestUtils;
import ribera.practicapartes.DAO.ProfesorDAO;
import ribera.practicapartes.Models.Profesor;
import ribera.practicapartes.Models.SessionManager;

import java.io.IOException;

import static ribera.practicapartes.Utils.AlertUtils.mostrarError;
import static ribera.practicapartes.Utils.CambioEscena.cambiarEscena;
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
            if (profesor == null) {
                System.out.println("Ha ocurrido un error con la base de datos.");
            } else {
                logIn();
            }
        } else {
            mostrarError("Los datos de inicio de sesión no son correctos.");
        }
    }

    void logIn() throws IOException {
        //mostrarAviso("Login correcto.");
        SessionManager.getInstance().setProfesorAutenticado(profesor);
        cambiarEscena(btnLogin, "parteVerde.fxml");
    }

}
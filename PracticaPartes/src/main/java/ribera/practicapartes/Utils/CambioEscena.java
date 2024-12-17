package ribera.practicapartes.Utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CambioEscena {

    // Método genérico para cambiar de escena con un botón
    public static void cambiarEscena(Button boton, String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(R.getUI(fxmlFile));  // Cargar el archivo FXML
            Parent root = fxmlLoader.load();  // Cargar el contenido FXML

            Scene scene = new Scene(root);  // Crear la escena con el contenido cargado
            Stage stage = (Stage) boton.getScene().getWindow();  // Obtener la ventana actual
            stage.setScene(scene);  // Cambiar la escena
        } catch (Exception e) {
            System.out.println("Error al cambiar la escena: " + e.getMessage());
        }
    }

    // Método genérico para abrir una nueva ventana con una nueva escena
    public static void abrirEscena(String fxmlFile, String titulo) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(R.getUI(fxmlFile));  // Cargar el archivo FXML
            Parent root = fxmlLoader.load();  // Cargar el contenido FXML

            Scene scene = new Scene(root);  // Crear la escena con el contenido cargado
            Stage stage = new Stage();  // Crear una nueva ventana
            stage.setScene(scene);  // Establecer la escena en la ventana
            stage.setTitle(titulo);  // Establecer el título de la ventana
            stage.show();  // Mostrar la ventana
        } catch (Exception e) {
            System.out.println("Error al cambiar la escena: " + e.getMessage());
        }
    }

    // Método para cambiar de escena y pasar el controlador con los datos
    public static void cambiarEscenaConDatos(Button boton, String fxmlFile, Object controlador) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(R.getUI(fxmlFile));
            fxmlLoader.setController(controlador);  // Establecer el controlador dinámicamente
            Parent root = fxmlLoader.load();  // Cargar la escena con el controlador

            Scene scene = new Scene(root);
            Stage stage = (Stage) boton.getScene().getWindow();  // Obtener la ventana actual
            stage.setScene(scene);  // Cambiar la escena
        } catch (Exception e) {
            System.out.println("Error al cambiar la escena con datos: " + e.getMessage());
        }
    }
}

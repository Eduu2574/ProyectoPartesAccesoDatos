package com.example.practicapartes;

import com.example.practicapartes.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.hibernate.exception.spi.Configurable;

public class CambioEscena {

    //metodo que cambia de escena en el FXML
    public static void cambiarEscena(Button boton, String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlFile));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) boton.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            System.out.println("Error al cambiar la escena.");
        }
    }


    public static void abrirEscena(String fxmlFile, String titulo) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlFile));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error al cambiar la escena.");

        }
    }


    public static void abrirEscena(String fxmlFile, String titulo, Configurable controller, Boolean estado) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            controller = fxmlLoader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error al cambiar la escena.");
        }
    }
}

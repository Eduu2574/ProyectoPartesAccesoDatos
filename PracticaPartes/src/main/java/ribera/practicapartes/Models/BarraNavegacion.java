package ribera.practicapartes.Models;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import ribera.practicapartes.Controllers.ParteVerdeController;
import ribera.practicapartes.Utils.R;

import java.io.IOException;
import java.util.Objects;

public class BarraNavegacion extends HBox {

    public BarraNavegacion() {
        Button crearParteButton = new Button("Crear Parte");
        Button listaPartesButton = new Button("Lista de Partes");
        Button listaAlumnosButton = new Button("Lista de Alumnos");
        Button crearProfesorButton = new Button("Crear Profesor");

        this.getChildren().addAll(crearParteButton, listaPartesButton, listaAlumnosButton, crearProfesorButton);
        HBox.setHgrow(crearParteButton, Priority.ALWAYS);
        HBox.setHgrow(listaPartesButton, Priority.ALWAYS);
        HBox.setHgrow(listaAlumnosButton, Priority.ALWAYS);
        HBox.setHgrow(crearProfesorButton, Priority.ALWAYS);

            crearParteButton.setOnAction(event -> {
                try {
                    navigateTo("parteVerde.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            listaPartesButton.setOnAction(event -> {
                try {
                    navigateTo("listaPartes.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            listaAlumnosButton.setOnAction(event -> {
                try {
                    navigateTo("listaAlumnos.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            crearProfesorButton.setOnAction(event -> {
                try {
                    navigateTo("login.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    private void navigateTo(String viewName) throws IOException {
        FXMLLoader loader = new FXMLLoader(R.getUI(viewName));
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


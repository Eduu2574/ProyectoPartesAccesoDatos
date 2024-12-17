package ribera.practicapartes;

import javafx.scene.image.Image;
import ribera.practicapartes.Controllers.listaAlumnosController;
import ribera.practicapartes.Controllers.listaPartesController;
import ribera.practicapartes.Utils.R;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ribera.practicapartes.Controllers.LoginController;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //cambiar estas dos lineas a login
        LoginController controller = new LoginController();
        FXMLLoader loader = new FXMLLoader(R.getUI("login.fxml"));

        loader.setController(controller);
        Scene scene = new Scene(loader.load());
        stage.setTitle("Gestión de Partes");
        scene.getStylesheets().add(R.getStyles("styles.css").toExternalForm());

        try {
            Image icono = new Image(Objects.requireNonNull(R.getImage("logo_icono.jpg")));
            stage.getIcons().add(icono);
        } catch (Exception e) {
            System.out.println("No se ha podido cargar el icono de la aplicación.");
        }

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
package com.proyectodi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Clase principal que arranca la aplicacion cargando la vista FXML
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Cargamos el fichero fxml con la ruta relativa a resources
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/proyectodi/fxml/main_view.fxml"));
        Parent root = loader.load();

        // Le damos un tamaño de 960x620 para que los botones y la tabla queden amplios y no se corten
        Scene scene = new Scene(root, 960, 620);

        stage.setTitle("Gestor de Incidencias y Tareas - 2º DAM");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

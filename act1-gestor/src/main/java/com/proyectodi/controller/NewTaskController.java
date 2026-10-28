package com.proyectodi.controller;

import com.proyectodi.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

// Controlador para la ventana modal de meter tareas nuevas (asi no se duplican por error en la principal)
public class NewTaskController {

    @FXML private TextField txtName;
    @FXML private TextField txtCategory;
    @FXML private TextField txtPrice;
    @FXML private ComboBox<String> cmbStatus;

    // Referencia a la lista principal de tareas que nos pasa el MainController
    private ObservableList<Item> masterData;

    @FXML
    public void initialize() {
        // Metemos los estados en el combo y ponemos Pendiente por defecto
        cmbStatus.setItems(FXCollections.observableArrayList("Pendiente", "En Progreso", "Completada"));
        cmbStatus.setValue("Pendiente");
    }

    // Metodo para recibir la lista desde la ventana principal (Registro IA #3 en docs/IA.md)
    public void setMasterData(ObservableList<Item> masterData) {
        this.masterData = masterData;
    }

    // Boton de guardar la nueva tarea
    @FXML
    private void handleSave() {
        String name = txtName.getText() != null ? txtName.getText().trim() : "";
        String category = txtCategory.getText() != null ? txtCategory.getText().trim() : "";
        String status = cmbStatus.getValue() != null ? cmbStatus.getValue() : "Pendiente";
        String priceStr = txtPrice.getText() != null ? txtPrice.getText().trim() : "0";

        // Comprobamos q el nombre no este vacio pa no meter cosas vacias
        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Debes escribir el nombre de la tarea.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        double price = 0.0;
        if (!priceStr.isEmpty()) {
            try {
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                // Si meten letras en las horas salta este aviso
                Alert alert = new Alert(Alert.AlertType.ERROR, "Las horas estimadas deben ser un numero.", ButtonType.OK);
                alert.showAndWait();
                return;
            }
        }

        // Sacamos el id mas alto sumando 1 (Registro IA #5 en docs/IA.md: bucle for clasico en vez de streams)
        int nextId = 1;
        if (masterData != null) {
            for (Item item : masterData) {
                if (item.getId() >= nextId) {
                    nextId = item.getId() + 1;
                }
            }
        }

        Item newItem = new Item(nextId, name, category, price, status);

        if (masterData != null) {
            masterData.add(newItem);
        }

        // Cerramos la ventanita modal despues de guardar
        closeWindow();
    }

    // Boton de cancelar: cerramos sin hacer nada
    @FXML
    private void handleCancel() {
        closeWindow();
    }

    // Funcion auxiliar para cerrar la ventana modal actual
    private void closeWindow() {
        Stage stage = (Stage) txtName.getScene().getWindow();
        if (stage != null) {
            stage.close();
        }
    }
}

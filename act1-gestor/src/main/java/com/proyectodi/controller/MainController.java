package com.proyectodi.controller;

import com.proyectodi.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    // Componentes del fxml q enlazamos con la vista (los 4 KPIs que pide el profe en el PDF)
    @FXML private Label lblTotalItems;
    @FXML private Label lblPendingCount;
    @FXML private Label lblInProgressCount;
    @FXML private Label lblCompletedCount;
    @FXML private Label lblFilteredCount;

    // Controles de filtrado
    @FXML private TextField txtSearchFilter;
    @FXML private ComboBox<String> cmbStatusFilter;

    // Tabla principal
    @FXML private TableView<Item> tableItems;
    @FXML private TableColumn<Item, Number> colId;
    @FXML private TableColumn<Item, String> colName;
    @FXML private TableColumn<Item, String> colCategory;
    @FXML private TableColumn<Item, Number> colPrice;
    @FXML private TableColumn<Item, String> colStatus;

    // Campos del formulario para ver y editar la tarea seleccionada
    @FXML private TextField txtDetailId;
    @FXML private TextField txtDetailName;
    @FXML private TextField txtDetailCategory;
    @FXML private TextField txtDetailPrice;
    @FXML private ComboBox<String> cmbDetailStatus;

    // Lista observable con todas las tareas
    private final ObservableList<Item> masterData = FXCollections.observableArrayList();
    
    // Lista filtrada para no machacar la lista original
    private FilteredList<Item> filteredData;

    @FXML
    public void initialize() {
        // Inicializamos todo al arrancar la ventana
        setupTableColumns();
        loadSampleData();
        setupFilters();
        setupMasterDetailBinding();
        updateKPIs();
    }

    // Enlace de columnas con properties (Registro IA #1 en docs/IA.md: buscado en Gemini xq no me salia a la primera)
    private void setupTableColumns() {
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        colName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colCategory.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        colPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        colStatus.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
    }

    // Metemos datos de prueba pa que la tabla no empiece vacia
    private void loadSampleData() {
        masterData.add(new Item(1, "Diseñar interfaz FXML", "Diseño", 4.0, "Pendiente"));
        masterData.add(new Item(2, "Configurar base de datos", "Backend", 6.5, "En Progreso"));
        masterData.add(new Item(3, "Implementar filtros y KPIs", "Frontend", 5.0, "Completada"));
        masterData.add(new Item(4, "Revisar documentación final", "Documentación", 2.0, "Pendiente"));

        // Opciones del combo del buscador
        cmbStatusFilter.setItems(FXCollections.observableArrayList("Todos", "Pendiente", "En Progreso", "Completada"));
        cmbStatusFilter.setValue("Todos");

        // Opciones del combo de edicion
        cmbDetailStatus.setItems(FXCollections.observableArrayList("Pendiente", "En Progreso", "Completada"));
        cmbDetailStatus.setValue("Pendiente");
    }

    // Configuramos los listeners del buscador y del combo de estado
    private void setupFilters() {
        filteredData = new FilteredList<>(masterData, p -> true);
        
        // Cada vez q escriben una letra en el buscador se lanza el filtro
        txtSearchFilter.textProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        
        // Y cuando cambian el combo de estado tambien
        cmbStatusFilter.valueProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        
        tableItems.setItems(filteredData);
    }

    // Logica del filtro en tiempo real (Registro IA #2 en docs/IA.md: apoyado x Gemini para combinar texto y combo)
    private void applyFilters() {
        // Pasamos el texto a minusculas pa que no falle si ponen mayusculas
        String searchText = txtSearchFilter.getText() == null ? "" : txtSearchFilter.getText().toLowerCase().trim();
        String selectedStatus = cmbStatusFilter.getValue();

        // Condicion para filtrar por texto o por combo
        filteredData.setPredicate(item -> {
            boolean matchesSearch = searchText.isEmpty()
                    || item.getName().toLowerCase().contains(searchText)
                    || item.getCategory().toLowerCase().contains(searchText);

            boolean matchesStatus = selectedStatus == null
                    || selectedStatus.equals("Todos")
                    || item.getStatus().equalsIgnoreCase(selectedStatus);

            return matchesSearch && matchesStatus;
        });

        // Refrescamos los kpis de arriba
        updateKPIs();
    }

    // Listener para que al pulsar una fila se rellene el formulario de la derecha solo
    private void setupMasterDetailBinding() {
        tableItems.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtDetailId.setText(String.valueOf(newSelection.getId()));
                txtDetailName.setText(newSelection.getName());
                txtDetailCategory.setText(newSelection.getCategory());
                txtDetailPrice.setText(String.valueOf(newSelection.getPrice()));
                cmbDetailStatus.setValue(newSelection.getStatus());
            } else {
                clearDetailFields();
            }
        });
    }

    // Abre una ventana modal para meter una tarea nueva desde cero
    @FXML
    private void handleOpenNewTaskWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/proyectodi/fxml/new_task.fxml"));
            Parent root = loader.load();

            // Paso de la lista al controller de la modal (Registro IA #3 en docs/IA.md)
            NewTaskController controller = loader.getController();
            controller.setMasterData(masterData);

            Stage modalStage = new Stage();
            // APPLICATION_MODAL bloquea la ventana de atras para no hacer cosas raras
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("Añadir Nueva Tarea");
            modalStage.setScene(new Scene(root, 440, 360));
            modalStage.setResizable(false);
            
            // showAndWait para esperar a q el usuario guarde o cancele
            modalStage.showAndWait();

            // Al cerrar la ventana modal refrescamos los contadores
            updateKPIs();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo abrir la ventana.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    // Boton azul para guardar los cambios de la tarea que tengamos seleccionada
    @FXML
    private void handleSaveChanges() {
        Item selectedItem = tableItems.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                selectedItem.setName(txtDetailName.getText());
                selectedItem.setCategory(txtDetailCategory.getText());
                selectedItem.setPrice(Double.parseDouble(txtDetailPrice.getText()));
                selectedItem.setStatus(cmbDetailStatus.getValue());

                // Refrescamos la tabla para que se vean los cambios
                tableItems.refresh();
                updateKPIs();

                // Mensajito de confirmacion pa que el usuario vea q se guardaron los cambios (buena practica de UX)
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "La tarea se ha actualizado correctamente.", ButtonType.OK);
                alert.setTitle("Tarea guardada");
                alert.setHeaderText(null);
                alert.showAndWait();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Las horas estimadas no son un numero valido.", ButtonType.OK);
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        } else {
            // Si pulsa guardar sin tener nada seleccionado le avisamos
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selecciona una tarea de la tabla para modificarla.", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    // Boton rojo para borrar la tarea seleccionada (con confirmacion previa para no liarla)
    @FXML
    private void handleDeleteItem() {
        Item selectedItem = tableItems.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Cuadro de confirmacion (en clase nos dijeron q borrar sin preguntar es mala UX)
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro que quieres eliminar la tarea \"" + selectedItem.getName() + "\"?", ButtonType.YES, ButtonType.NO);
            confirm.setTitle("Confirmar eliminación");
            confirm.setHeaderText(null);
            confirm.showAndWait();

            if (confirm.getResult() == ButtonType.YES) {
                masterData.remove(selectedItem);
                clearDetailFields();
                updateKPIs();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selecciona una tarea de la tabla para eliminarla.", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    // Boton para deseleccionar la fila actual y limpiar el panel
    @FXML
    private void handleClearFields() {
        tableItems.getSelectionModel().clearSelection();
        clearDetailFields();
    }

    // Boton para borrar el buscador y resetear el filtro
    @FXML
    private void handleClearFilters() {
        txtSearchFilter.clear();
        cmbStatusFilter.setValue("Todos");
    }

    // Funcion para refrescar los 4 contadores de arriba (Registro IA #5 en docs/IA.md: bucle for clasico en vez de streams)
    private void updateKPIs() {
        // Total de tareas
        lblTotalItems.setText(String.valueOf(masterData.size()));

        // Contamos cada estado con un for clasico pa que no quede muy artificial
        int pendientes = 0;
        int enProgreso = 0;
        int completadas = 0;

        for (Item item : masterData) {
            if ("Pendiente".equalsIgnoreCase(item.getStatus())) {
                pendientes++;
            } else if ("En Progreso".equalsIgnoreCase(item.getStatus())) {
                enProgreso++;
            } else if ("Completada".equalsIgnoreCase(item.getStatus())) {
                completadas++;
            }
        }

        lblPendingCount.setText(String.valueOf(pendientes));
        lblInProgressCount.setText(String.valueOf(enProgreso));
        lblCompletedCount.setText(String.valueOf(completadas));

        // Tareas q se ven ahora mismo segun el filtro
        int visibles = filteredData != null ? filteredData.size() : masterData.size();
        lblFilteredCount.setText("(" + visibles + " visibles)");
    }

    // Resetea las cajas de texto de la derecha
    private void clearDetailFields() {
        txtDetailId.clear();
        txtDetailName.clear();
        txtDetailCategory.clear();
        txtDetailPrice.clear();
        cmbDetailStatus.setValue("Pendiente");
    }
}

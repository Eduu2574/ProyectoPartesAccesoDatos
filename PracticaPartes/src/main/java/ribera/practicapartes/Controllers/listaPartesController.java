package ribera.practicapartes.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.PropertySheet;
import ribera.practicapartes.DAO.PartesDAO;
import ribera.practicapartes.Models.ParteIncidencia;
import ribera.practicapartes.Models.ParteVerManager;
import ribera.practicapartes.Utils.AlertUtils;
import ribera.practicapartes.Utils.CambioEscena;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class listaPartesController {
    @FXML
    public TableView tabla_partes;
    @FXML
    public TableColumn col_expediente;
    @FXML
    public TableColumn col_alumno;
    @FXML
    public TableColumn col_grupo;
    @FXML
    public TableColumn col_profesor;
    @FXML
    public TableColumn col_fecha;
    @FXML
    public TableColumn col_descripcion;
    @FXML
    public TableColumn col_sancion;
    @FXML
    public Pagination pagination;
    @FXML
    public TextField expedienteBuscar;
    @FXML
    public DatePicker fechaInicio;
    @FXML
    public DatePicker fechaFin;
    @FXML
    public Button btnFechas;
    @FXML
    public Button btnNumeroExpediente;
    @FXML
    public Button btnLimpiar;
    @FXML
    public TableColumn col_botonVerMas;

    public ObservableList<ParteIncidencia> partes = FXCollections.observableArrayList();
    private final int filas_pagina = 5;
    DateTimeFormatter formateadorFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void initialize() {
        col_expediente.setCellValueFactory(new PropertyValueFactory<>("expediente_alumno"));
        col_alumno.setCellValueFactory(new PropertyValueFactory<>("alumno"));
        col_grupo.setCellValueFactory(new PropertyValueFactory<>("grupo"));
        col_profesor.setCellValueFactory(new PropertyValueFactory<>("id_profesor"));
        col_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        col_descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        col_sancion.setCellValueFactory(new PropertyValueFactory<>("sancion"));

        partes.addAll(PartesDAO.cargarPartes());

        tabla_partes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tabla_partes.setItems(partes);

        tabla_partes.setRowFactory(tv -> new TableRow<ParteIncidencia>() {
            @Override
            protected void updateItem(ParteIncidencia item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setStyle(""); // Restablece el estilo para filas vacías
                } else {
                    String color = item.getColor().getCodigo_color();
                    if (color != null && !color.isEmpty()) {
                        setStyle("-fx-background-color: " + color + ";");
                    } else {
                        setStyle("");
                    }
                }
            }
        });

        col_botonVerMas.setCellFactory(param -> new TableCell<ParteIncidencia, Void>() {
            private final Button btn = new Button("Ver");

            {
                btn.setOnAction(event -> {
                    ParteIncidencia item = getTableRow().getItem(); // Obtener el item asociado a la fila
                    if (item != null) {
                        try {
                            ParteVerManager.getInstance().setParteSeleccionado(item);
                            CambioEscena.cambiarEscena(btn, "parteVer.fxml");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                btn.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white; -fx-border-radius: 5px; -fx-font-size: 14px; -fx-padding: 5px 10px;");
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });

        // Configurar la paginación
        int pageCount = (int) Math.ceil(partes.size() / (double) filas_pagina);
        pagination.setPageCount(pageCount);
        pagination.setPageFactory(this::crearPagina);

        // Mostrar la primera página
        mostrarPagina(0, partes);
    }

    private TableView<PropertySheet.Item> crearPagina(int indice) {
        mostrarPagina(indice, partes);
        return tabla_partes;
    }

    private void mostrarPagina(int indice, ObservableList<ParteIncidencia> lista) {
        int a = indice * filas_pagina;
        int b = Math.min(a + filas_pagina, lista.size());
        tabla_partes.setItems(FXCollections.observableArrayList(lista.subList(a, b)));
        tabla_partes.refresh();
    }

    @FXML
    public void onBtnFechas(ActionEvent event) {
        if (fechaInicio.getValue() == null && fechaFin.getValue() != null|| fechaFin.getValue() == null && fechaInicio.getValue() != null) {
            AlertUtils.mostrarError("Rellene los dos campos de fecha");
        } else if (fechaInicio.getValue() == null && fechaFin.getValue() == null) {
            tabla_partes.setItems(partes);

            int pageCount = (int) Math.ceil(partes.size() / (double) filas_pagina);
            pagination.setPageCount(pageCount);
            pagination.setPageFactory(this::crearPagina);

            mostrarPagina(0, partes);
        } else {
            ObservableList<ParteIncidencia> filtradaFecha = FXCollections.observableArrayList();
            for (ParteIncidencia a: partes) {
                LocalDate fecha = LocalDate.parse(a.getFecha(), formateadorFecha);
                if (!fecha.isBefore(fechaInicio.getValue()) && !fecha.isAfter(fechaFin.getValue())) {
                    filtradaFecha.add(a);
                }
            }

            tabla_partes.setItems(filtradaFecha);

            int pageCount = (int) Math.ceil(filtradaFecha.size() / (double) filas_pagina);
            pagination.setPageCount(pageCount);
            pagination.setPageFactory(index -> {
                mostrarPagina(index, filtradaFecha);
                return tabla_partes;
            });

            mostrarPagina(0, filtradaFecha);
        }
    }

    @FXML
    public void onBtnNumeroExpediente(ActionEvent event) {
        if (!expedienteBuscar.getText().isEmpty()) {
            ObservableList<ParteIncidencia> filtradaExp = FXCollections.observableArrayList();
            for (ParteIncidencia a: partes) {
                String exp = a.getExpediente_alumno();
                if (expedienteBuscar.getText().equals(exp)) {
                    filtradaExp.add(a);
                }
            }

            tabla_partes.setItems(filtradaExp);

            int pageCount = (int) Math.ceil(filtradaExp.size() / (double) filas_pagina);
            pagination.setPageCount(pageCount);
            pagination.setPageFactory(index -> {
                mostrarPagina(index, filtradaExp);
                return tabla_partes;
            });

            mostrarPagina(0, filtradaExp);
        } else {
            tabla_partes.setItems(partes);

            int pageCount = (int) Math.ceil(partes.size() / (double) filas_pagina);
            pagination.setPageCount(pageCount);
            pagination.setPageFactory(this::crearPagina);

            mostrarPagina(0, partes);
        }
    }
}
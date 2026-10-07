package com.example.hellofx;
import javafx.application.Application;
import
        javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class CustomerManagerApp extends
        Application {
    @Override
    public void start(Stage stage) {
        // Step 2: the list
        var customers = FXCollections.
                <Customer>observableArrayList();
        // Step 1: form
        TextField nameField = new
                TextField();
        nameField.setPromptText("Name");
        ChoiceBox<String> provinceBox = new
                ChoiceBox<>
                (FXCollections.observableArrayList(
                        "Central", "Copperbelt",
                        "Eastern", "Luapula", "Lusaka",
                        "Muchinga", "Northern",
                        "North-Western", "Southern", "Western"));
        Button addButton = new
                Button("Add");
        addButton.setDefaultButton(true);
// Enter key = Add
        Button deleteButton = new
                Button("Delete");
        // Step 3: table
        TableView<Customer> table = new
                TableView<>(customers);
        TableColumn<Customer, String>
                nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(c ->
                new
                        SimpleStringProperty(c.getValue().getName()
                ));
        TableColumn<Customer, String>
                provCol = new TableColumn<>("Province");
        provCol.setCellValueFactory(c ->
                new
                        SimpleStringProperty(c.getValue().getProvince()));
        table.getColumns().addAll(nameCol,
                provCol);
        // Step 4: validate, then add
        addButton.setOnAction(e -> {
            if
            (nameField.getText().isBlank() ||
                    provinceBox.getValue() == null) {
                new
                        Alert(Alert.AlertType.WARNING, "Enter a name and choose a province.").show();
                return;
            }
            customers.add(new
                    Customer(nameField.getText().trim(),
                    provinceBox.getValue()));
            nameField.clear();
        });
        // Step 5: confirm, then delete
        deleteButton.setOnAction(e -> {
            Customer selected =
                    table.getSelectionModel().getSelectedItem()
                    ;
            if (selected != null) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + selected.getName() + "?");
                        confirm.showAndWait().filter(r -> r ==
                                ButtonType.OK).ifPresent(r ->
                                customers.remove(selected));
            }
        });
        VBox root = new VBox(10, nameField,
                provinceBox, addButton, deleteButton,
                table);
        stage.setScene(new Scene(root, 400,
                450));
        stage.setTitle("Customer Manager");
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import static main.Inventory.*;
import static main.Main.*;

/**This class creates a controller for AddProduct.fxml*/
public class AddProductController implements Initializable {
    private ObservableList<Part> associatedPartsList= FXCollections.observableArrayList();
    public TableColumn associatedPartId;
    public TableColumn associatedPartName;
    public TableColumn associatedPartInventory;
    public TableColumn associatedPartPrice;
    public TextField productIdInput;
    public TextField productNameInput;
    public TextField productInventoryInput;
    public TextField productPriceInput;
    public TextField productMaxInput;
    public TextField productMinInput;
    public Button removeAssociatedPartButton;
    public Button productSaveButton;
    public Button productCancelButton;
    public TextField partSearch;
    public TableView availablePartsTable;
    public TableView associatedPartsTable;
    public TableColumn partId;
    public TableColumn partName;
    public TableColumn partInventory;
    public TableColumn partPrice;
    public Part selectedPart = null;

    /**
     * The event handler method for the Save button.
     * This method assigns the text field inputs to  variables for use in the Product class constructor.
     * The method uses various forms of logic to ensure the texts fields are all filled out and contain the correct data types, if not a dialogue box is displayed with the error that needs to be corrected.
     * A new Product object is created and Inventory.addProduct is called().
     * The method then calls returnToMainForm() so the user is directed back to the MainForm.
     * This method throws and IOException to catch any IO errors that would crash or interrupt the program.
     *
     * RUNTIME ERROR: If an integer was not entered into Price/Cost, Inventory, Max, or Min a NumberFormatException was generated.
     * In order to resolve this issue I used a Try-Catch block to catch the error and display a dialogue box informing the user what needed correction.
     * This error also occurred in the AddPartController, the ModifyPartController and the ModifyProductController with the same solution being implemented.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void onProductSaveButton(ActionEvent actionEvent) throws IOException {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Add Product Error");
        error.setHeaderText(null);
        error.setContentText("Please enter data into all text fields");

        Alert inventoryError = new Alert(Alert.AlertType.ERROR);
        inventoryError.setTitle("Add Product Error");
        inventoryError.setHeaderText(null);
        inventoryError.setContentText("Min must be less than Max. \nInventory must be in between Min & Max");

        Alert dataTypeError = new Alert(Alert.AlertType.ERROR);
        dataTypeError.setTitle("Invalid Data Type Error");
        dataTypeError.setHeaderText(null);
        dataTypeError.setContentText("Price/Cost must be a number in decimal form: 1.25, .50, etc.\n Inventory must be a number.\n Max must be a number.\n Min must be a number.\n");
        try {
            if (productNameInput.getText() == "" || productPriceInput.getText() == "" || productInventoryInput.getText() == "" || productMinInput.getText() == "" || productMaxInput.getText() == "") {
                error.showAndWait();
            } else {
                String productName = productNameInput.getText();
                double productPrice = Double.parseDouble(productPriceInput.getText());

                if (Integer.parseInt(productMinInput.getText()) > Integer.parseInt(productMaxInput.getText()) ||
                        Integer.parseInt(productMinInput.getText()) > Integer.parseInt(productInventoryInput.getText()) ||
                        Integer.parseInt(productInventoryInput.getText()) > Integer.parseInt(productMaxInput.getText())) {
                    inventoryError.showAndWait();
                } else {
                    int productInventory = Integer.parseInt(productInventoryInput.getText());
                    int productMin = Integer.parseInt(productMinInput.getText());
                    int productMax = Integer.parseInt(productMaxInput.getText());

                    Product product = new Product(uniqueProductId, productName, productPrice, productInventory, productMin, productMax);

                    for (Part p : associatedPartsList) {
                        product.addAssociatedPart(p);
                    }

                    Inventory.addProduct(product);
                    uniqueProductId++;
                    returnToMainForm(actionEvent);
                }
            }
        }
        catch (NumberFormatException e){
            dataTypeError.showAndWait();
        }
    }
    /**
     * The event handler method for the Cancel button.
     * This method calls returnToMainForm() so the user is directed back to the MainForm.
     * This method throws and IOException to catch any IO errors that would crash or interrupt the program.
     * A new Product object is not created and any data in the text fields is not used.
     * @param actionEvent
     * @throws IOException
     */
    public void onProductCancelButton(ActionEvent actionEvent) throws IOException {
        returnToMainForm(actionEvent);
    }

    /**
     * The event handler method for the Add button.
     * This method takes the selected Part from the availablePartsTable and adds it to the associatedPartsList.
     * If the selected Part is null (no Part selected) the method returns without adding anything to the associatedPartsList
     * @param actionEvent
     */
    public  void onAddAssociatedPart(ActionEvent actionEvent) {
        Part sp = (Part) availablePartsTable.getSelectionModel().getSelectedItem();

        if(sp == null){
            return;
        }
        associatedPartsList.add(sp);
    }

    /**
     * The event handler method for the availablePartsTable search field.
     * To search by Part name (full or partial) a variable is created from the search field input and passed to Inventory.lookupPart(). The result is added to a new observable list.
     * To search by Part id (exact match) the variable is passed through Integer.parse(). That result is passed through Inventory.lookupPart(). This result is added to the new observable list.
     * The new observable list is then displayed on the availablePartsTable.
     * @param actionEvent
     */
    public void onPartSearchEnter(ActionEvent actionEvent) {
        String s = partSearch.getText();

        ObservableList<Part> p = lookupPart(s);

        if(p.size() == 0) {
            try {
                int partId = Integer.parseInt(s);
                Part part = lookupPart(partId);
                if (part != null)
                    p.add(part);
            } catch (NumberFormatException e) {
                //ignore
            }
        }
        availablePartsTable.setItems(p);
        partSearch.setText("");
    }
    /**
     * The event handler method for the Remove Associated Part button.
     * This method takes the selected Part from the associatedPartsTable and removes it from the associatedPartsList.
     * If the selected part is null (no Part selected) the method returns without removing anything.
     * Dialog boxes are used to confirm the users choice or to show there is no Part to delete.
     * @param actionEvent
     */
    public void onRemoveAssociatedPartButton(ActionEvent actionEvent) {
        selectedPart = (Part) associatedPartsTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete selected associated part?");

        Optional<ButtonType> result = alert.showAndWait();

        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Delete Error");
        info.setHeaderText("No Data To Delete");
        info.setContentText("Selected Part contains no data");

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (selectedPart == null) {
                info.showAndWait();
            }
            associatedPartsList.remove(selectedPart);
        }
    }

    /**
     * The first method called when the AddProductController is initialized.
     * This method sets the data that will populate the availablePartsTable and associatedPartsTable.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        availablePartsTable.setItems(main.Inventory.getAllParts());
        associatedPartsTable.setItems(associatedPartsList);

        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}

package controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import static main.Inventory.*;
/**This class creates a controller for MainForm.fxml*/
public class MainFormController implements Initializable {
    public TableView<Part> allPartsTable;
    public TableColumn partId;
    public TableColumn partName;
    public TableColumn partInventory;
    public TableColumn partPrice;
    public Button openAddPart;
    public Button openModifyPart;
    public Button deletePart;
    public TableView<Product> allProductsTable;
    public TableColumn productId;
    public TableColumn productName;
    public TableColumn productInventory;
    public TableColumn productPrice;
    public Button openAddProduct;
    public Button openModifyProduct;
    public Button deleteProduct;
    public TextField partSearch;
    public TextField productSearch;
    public Button exitButton;
    public Part selectedPart = null;
    public static Product selectedProduct = null;

    /**
     * The event handler method for the availablePartsTable search field.
     * To search by Part name (full or partial) a variable is created from the search field input and passed to Inventory.lookupPart(). The result is added to a new observable list.
     * To search by Part id (exact match) the variable is passed through Integer.parse(). That result is passed through Inventory.lookupPart(). This result is added to the new observable list.
     * The new observable list is then displayed on the allPartsTable.
     * @param actionEvent
     */
    public void onPartSearchEnter (ActionEvent actionEvent){
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

        allPartsTable.setItems(p);
        partSearch.setText("");
    }
    /**
     * The event handler method for the availablePartsTable search field.
     * To search by Product name (full or partial) a variable is created from the search field input and passed to Inventory.lookupProduct(). The result is added to a new observable list.
     * To search by Product id (exact match) the variable is passed through Integer.parse(). That result is passed through Inventory.lookupProduct(). This result is added to the new observable list.
     * The new observable list is then displayed on the allProductsTable.
     * @param actionEvent
     */
    public void onProductSearchEnter (ActionEvent actionEvent){
        String s = productSearch.getText();

        ObservableList<Product> p = lookupProduct(s);

        if(p.size() == 0) {
            try {
                int productId = Integer.parseInt(s);
                Product product = lookupProduct(productId);
                if (product != null)
                    p.add(product);
            } catch (NumberFormatException e) {
                //ignore
            }
        }

        allProductsTable.setItems(p);
        productSearch.setText("");
    }

    /**
     * The event handler method for the openAddPart button.
     * This method loads AddPart.fxml, calls a stage, creates a scene, and then displays the scene on the stage.
     * This method throws and IOException to catch any IO errors that would crash or interrupt the program.
     * @param actionEvent
     * @throws IOException
     */
    public void onOpenAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * The event handler method for the openModifyPart button.
     * This method takes a selected Part, finds its index, passes that information to the ModifyPartController via passPartSelection(), loads ModifyPart.fxml, calls a stage, creates a scene, and then displays the scene on the stage.
     * If the selected part is null (no part selected) then a dialogue box alerts the user.
     * This method throws and IOException to catch any IO errors that would crash or interrupt the program.
     * @param actionEvent
     * @throws IOException
     */
    public void onOpenModifyPart(ActionEvent actionEvent) throws IOException {
        selectedPart = allPartsTable.getSelectionModel().getSelectedItem();
        int selectedIndex = allPartsTable.getSelectionModel().getSelectedIndex();
        ModifyPartController.passPartSelection(selectedPart, selectedIndex);

        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Modify Error");
        error.setHeaderText("No Data To Modify");
        error.setContentText("Please select a Part to modify");

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            error.showAndWait();
        }
    }

    /**
     * The event handler method for the deletePart button.
     * This method takes a selected Part and passes it through Inventory.deletePart().
     * Users are asked to confirm the action before Inventory.deletePart() is called.
     * If the selected part is null (no part selected) then a dialogue box alerts the user.
     * @param actionEvent
     */
    public void onDeletePart(ActionEvent actionEvent) {
        selectedPart = allPartsTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete selected part?");

        Optional<ButtonType> result = alert.showAndWait();

        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Delete Error");
        error.setHeaderText("No Data To Delete");
        error.setContentText("Selected Part contains no data");

        if(result.isPresent() && result.get() == ButtonType.OK) {
            if (!deletePart(selectedPart))
                error.showAndWait();
        }
    }
    /**
     * The event handler method for the openAddProduct button.
     * This method loads AddProduct.fxml, calls a stage, creates a scene, and then displays the scene on the stage.
     * This method throws and IOException to catch any IO errors that would crash or interrupt the program.
     * @param actionEvent
     * @throws IOException
     */
    public void onOpenAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * The event handler method for the openModifyProduct button.
     * This method takes a selected Product, finds its index, passes that information to the ModifyProductController via passProductSelection(), loads ModifyProduct.fxml, calls a stage, creates a scene, and then displays the scene on the stage.
     * If the selected product is null (no product selected) then a dialogue box alerts the user.
     * This method throws and IOException to catch any IO errors that would crash or interrupt the program.
     * @param actionEvent
     * @throws IOException
     */
    public void onOpenModifyProduct(ActionEvent actionEvent) {
        selectedProduct = allProductsTable.getSelectionModel().getSelectedItem();
        int selectedIndex = allProductsTable.getSelectionModel().getSelectedIndex();
        ModifyProductController.passProductSelection(selectedProduct, selectedIndex);

        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Modify Error");
        error.setHeaderText("No Data To Modify");
        error.setContentText("Please select a Part to modify");

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            error.showAndWait();
        }
    }
    /**
     * The event handler method for the deleteProduct button.
     * This method takes a selected Product and passes it through Inventory.deleteProduct().
     * Users are asked to confirm the action before Inventory.deleteProduct() is called.
     * If the selected part is null (no part selected) then a dialogue box alerts the user.
     * @param actionEvent
     */
    public void onDeleteProduct(ActionEvent actionEvent) {
        Product selectedProduct = allProductsTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete selected product?");

        Optional<ButtonType> result = alert.showAndWait();

        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Delete Error");
        error.setHeaderText("No Data To Delete");
        error.setContentText("Selected Product contains no data");

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (!deleteProduct(selectedProduct)) {
                error.showAndWait();
            }
        }
    }

    /**
     * The event handler method for the Exit button.
     * This method exits the program without any other actions.
     * @param actionEvent
     */
    public void onExitButton(ActionEvent actionEvent){
        Platform.exit();
    }
    /**
     * The first method called when the MainFormController is initialized.
     * This method sets the data that will populate the allPartsTable and allProductsTable.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allPartsTable.setItems(main.Inventory.getAllParts());
        allProductsTable.setItems(main.Inventory.getAllProducts());

        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));



    }


}

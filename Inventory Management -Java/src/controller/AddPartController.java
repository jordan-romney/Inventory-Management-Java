package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import main.InHouse;
import main.Outsourced;
import java.io.IOException;
import static main.Inventory.addPart;
import static main.Main.returnToMainForm;
import static main.Main.uniquePartId;

/**This class creates a controller for AddPart.fxml*/
public class AddPartController {
    public RadioButton inHouseRadio;
    public ToggleGroup addPart;
    public RadioButton outsourcedRadio;
    public Button partSaveButton;
    public Button partCancelButton;
    public Label partSourceLabel;
    public TextField partNameInput;
    public TextField partInventoryInput;
    public TextField partPriceInput;
    public TextField partMaxInput;
    public TextField partSourceInput;
    public TextField partMinInput;

    /**
     * The event handler method for the In House radio button.
     * This method changes the partSourceLabel text to Machine ID.
     * @param actionEvent
     */
    public void onInHouseRadio(ActionEvent actionEvent) {
        partSourceLabel.setText("Machine ID");
    }
    /**
     * The event handler method for the Outsourced radio button.
     * This method changes the partSourceLabel text to Company Name.
     * @param actionEvent
     */
    public void onOutsourcedRadio(ActionEvent actionEvent) {
        partSourceLabel.setText("Company Name");
    }

    /**
     * The event handler method for the Save button.
     * This method assigns the text field inputs to variables for use in the Part class constructor.
     * The method uses various forms of logic to ensure the texts fields are all filled out and use the correct data types, if not a dialogue box is displayed with the error that needs to be corrected.
     * A Part object is created and Inventory.addPart() is called.
     * The method then calls returnToMainForm() so the user is directed back to the MainForm after the new Part object is created.
     * This method throws and IOException to catch any IO errors that would crash or interrupt the program.
     *
     * RUNTIME ERROR: If an integer was not entered into Price/Cost, Inventory, Max, or Min a NumberFormatException was generated.
     * In order to resolve this issue I used a Try-Catch block to catch the error and display a dialogue box informing the user what needed correction.
     * This error also occurred in the ModifyPartController, the AddProductController and the ModifyProductController with the same solution being implemented.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void onPartSaveButton(ActionEvent actionEvent) throws IOException {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Add Part Error");
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

        try{
            if (partNameInput.getText() == "" || partPriceInput.getText() == "" || partInventoryInput.getText() == "" || partMinInput.getText() == "" || partMaxInput.getText() == "" || partSourceInput.getText() == "") {
                error.showAndWait();
            } else {
                String partName = partNameInput.getText();
                double partPrice = Double.parseDouble(partPriceInput.getText());

                if (Integer.parseInt(partMinInput.getText()) > Integer.parseInt(partMaxInput.getText()) ||
                        Integer.parseInt(partMinInput.getText()) > Integer.parseInt(partInventoryInput.getText()) ||
                        Integer.parseInt(partInventoryInput.getText()) > Integer.parseInt(partMaxInput.getText())) {
                    inventoryError.showAndWait();
                } else {
                    int partInventory = Integer.parseInt(partInventoryInput.getText());
                    int partMin = Integer.parseInt(partMinInput.getText());
                    int partMax = Integer.parseInt(partMaxInput.getText());
                    String machineId = partSourceInput.getText();
                    String companyName = partSourceInput.getText();

                    if (inHouseRadio.isSelected()) {
                        InHouse iPart = new InHouse(uniquePartId, partName, partPrice, partInventory, partMin, partMax, machineId);
                        addPart(iPart);
                    } else if (outsourcedRadio.isSelected()) {
                        Outsourced oPart = new Outsourced(uniquePartId, partName, partPrice, partInventory, partMin, partMax, companyName);
                        addPart(oPart);
                    }
                    uniquePartId++;
                    returnToMainForm(actionEvent);
                }
            }
        }
        catch(NumberFormatException e){
            dataTypeError.showAndWait();
        }
    }

    /**
     * The event handler method for the Cancel button.
     * This method calls returnToMainForm() so the user is directed back to the MainForm.
     * This method throws and IOException to catch any IO errors that would crash or interrupt the program.
     * A new Part object is not created and any data in the text fields is not used.
     * @param actionEvent
     * @throws IOException
     */
    public void onPartCancelButton(ActionEvent actionEvent) throws IOException {
        returnToMainForm(actionEvent);
    }
}
package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static main.Inventory.*;
import static main.Main.returnToMainForm;

/**This class creates a controller for ModifyPart.fxml*/
public class ModifyPartController implements Initializable {
    public RadioButton inHouseRadio;
    public ToggleGroup modifyPart;
    public RadioButton outsourcedRadio;
    public TextField partIdInput;
    public TextField partNameInput;
    public TextField partInventoryInput;
    public TextField partPriceInput;
    public TextField partMaxInput;
    public TextField partSourceInput;
    public TextField partMinInput;
    public Label partSourceLabel;

    public static Part sp = null;
    public static int partIndex = 0;

    /**
     * The method for getting Part selection data from another controller.
     * This method takes in a Part and an integer and sets them to variables for this controller to access.
     * @param selection the Part object being passed
     * @param selectedIndex the integer of the Part's index position
     */
    public static void passPartSelection(Part selection, int selectedIndex){
        sp = selection;
        partIndex = selectedIndex;
    }

    /**
     * The event handler method for the In House radio button.
     * When the In House radio button is selected the partSourceLabel text is changed.
     * @param actionEvent
     */
    public void onInHouseRadio(ActionEvent actionEvent) {
        partSourceLabel.setText("Machine ID");
    }
    /**
     * The event handler method for the Outsourced radio button.
     * When the Outsourced radio button is selected the partSourceLabel text is changed.
     * @param actionEvent
     */
    public void onOutsourcedRadio(ActionEvent actionEvent) {
        partSourceLabel.setText("Company Name");
    }
    /**
     * The event handler method for the Save button.
     * This method passes the text field inputs to the setter methods of the current Part object.
     * If the radio buttons are changed the current object is deleted and a new object is created with the text field inputs and passed to Inventory.addPart().
     * The method uses various forms of logic to ensure the texts fields are all filled out and contain the correct data types, if not a dialogue box is displayed with the error that needs to be corrected.
     * partIndex and the current Part are passed into Inventory.updatePart().
     * The method then calls returnToMainForm() so the user is directed back to the MainForm after the current Part object is updated.
     * This method throws and IOException to catch any IO errors that would crash or interrupt the program.
     *
     * RUNTIME ERROR: If an integer was not entered into Price/Cost, Inventory, Max, or Min a NumberFormatException was generated.
     * In order to resolve this issue I used a Try-Catch block to catch the error and display a dialogue box informing the user what needed correction.
     * This error also occurred in the AddPartController, the AddProductController and the ModifyProductController with the same solution being implemented.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void onPartSaveButton(ActionEvent actionEvent) throws IOException {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Modify Part Error");
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
            if (partNameInput.getText() == "" || partPriceInput.getText() == "" || partInventoryInput.getText() == "" || partMinInput.getText() == "" || partMaxInput.getText() == "" || partSourceInput.getText() == "") {
                error.showAndWait();
            } else {
                if (Integer.parseInt(partMinInput.getText()) > Integer.parseInt(partMaxInput.getText()) ||
                        Integer.parseInt(partMinInput.getText()) > Integer.parseInt(partInventoryInput.getText()) ||
                        Integer.parseInt(partInventoryInput.getText()) > Integer.parseInt(partMaxInput.getText())) {
                    inventoryError.showAndWait();
                } else {
                    if (inHouseRadio.isSelected() && sp instanceof Outsourced) {
                        deletePart(sp);
                        InHouse IPart = new InHouse(
                                Integer.parseInt(partIdInput.getText()),
                                partNameInput.getText(),
                                Double.parseDouble(partPriceInput.getText()),
                                Integer.parseInt(partInventoryInput.getText()),
                                Integer.parseInt(partMinInput.getText()),
                                Integer.parseInt(partMaxInput.getText()),
                                partSourceInput.getText());
                        addPart(IPart);
                        returnToMainForm(actionEvent);
                    } else if (outsourcedRadio.isSelected() && sp instanceof InHouse) {
                        deletePart(sp);
                        Outsourced oPart = new Outsourced(
                                Integer.parseInt(partIdInput.getText()),
                                partNameInput.getText(),
                                Double.parseDouble(partPriceInput.getText()),
                                Integer.parseInt(partInventoryInput.getText()),
                                Integer.parseInt(partMinInput.getText()),
                                Integer.parseInt(partMaxInput.getText()),
                                partSourceInput.getText());
                        addPart(oPart);
                        returnToMainForm(actionEvent);
                    } else {
                        sp.setId(Integer.parseInt(partIdInput.getText()));
                        sp.setName(partNameInput.getText());
                        sp.setPrice(Double.parseDouble(partPriceInput.getText()));
                        sp.setStock(Integer.parseInt(partInventoryInput.getText()));
                        sp.setMin(Integer.parseInt(partMinInput.getText()));
                        sp.setMax(Integer.parseInt(partMaxInput.getText()));

                        if (inHouseRadio.isSelected()) {
                            ((InHouse) sp).setMachineId((partSourceInput.getText()));
                        } else if (outsourcedRadio.isSelected()) {
                            ((Outsourced) sp).setCompanyName((partSourceInput.getText()));
                        }
                        updatePart(partIndex, sp);
                        returnToMainForm(actionEvent);
                    }
                }
            }
        } catch (NumberFormatException e) {
            dataTypeError.showAndWait();
        }
    }
    /**
     * The event handler method for the Cancel button.
     * This method calls returnToMainForm() so the user is directed back to the MainForm.
     * This method throws and IOException to catch any IO errors that would crash or interrupt the program.
     * The current Part object is not updated and any data in the text fields is not used.
     * @param actionEvent
     * @throws IOException
     */
    public void onPartCancelButton(ActionEvent actionEvent) throws IOException {
        returnToMainForm(actionEvent);
    }
    /**
     * The first method called when the ModifyPartController is initialized.
     * This method sets the data that will populate the text fields of the Modify Part screen.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partIdInput.setText(Integer.toString(sp.getId()));
        partNameInput.setText(sp.getName());
        partInventoryInput.setText(Integer.toString(sp.getStock()));
        partPriceInput.setText(Double.toString(sp.getPrice()));
        partMaxInput.setText(Integer.toString(sp.getMax()));
        partMinInput.setText(Integer.toString(sp.getMin()));

        if(sp instanceof Outsourced){
            outsourcedRadio.setSelected(true);
            partSourceLabel.setText("Company Name");
            partSourceInput.setText(((Outsourced) sp).getCompanyName());
        }
        else if(sp instanceof InHouse){
            inHouseRadio.setSelected(true);
            partSourceLabel.setText("Machine ID");
            partSourceInput.setText(((InHouse) sp).getMachineId());
        }



    }
}

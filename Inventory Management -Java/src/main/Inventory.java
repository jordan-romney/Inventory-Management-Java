package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import static main.Main.uniquePartId;
import static main.Main.uniqueProductId;

/**
 * This class creates members and methods for Inventory objects.
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * The method for adding Parts to the allParts observable list.
     *
     * @param newPart the new Part to add
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * The method for adding Products to the allProducts observable list.
     *
     * @param newProduct the new Product to add
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * The method for looking up a Part in the allParts observable list by partID.
     * This method iterates through the allParts list and compares the passed in partID with current Part object's partId.
     *
     * @param partId the partId to look up
     * @return either null or the Part corresponding to the passed in partId
     */
    public static Part lookupPart(int partId) {
        for (Part p : allParts) {
            if (p.getId() == partId) {
                return p;
            }
        }
        return null;
    }

    /**
     * The method for looking up a Product in the allProducts observable list by productID.
     * This method iterates through the allProducts list and compares the passed in productID with current Product object's productId.
     *
     * @param productId the productId to look up
     * @return either null or the Product corresponding to the passed in productId
     */
    public static Product lookupProduct(int productId) {
        for (Product p : allProducts) {
            if (p.getId() == productId) {
                return p;
            }
        }
        return null;
    }

    /**
     * The method for looking up a Part in the allParts observable list by partName.
     * This method iterates through the allParts list and compares the passed in string with current Part object's partName.
     * If the string contains the full or partial partName it is added to a new observable list.
     *
     * @param partName the partId to look up
     * @return namedPart observable list
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> namedPart = FXCollections.observableArrayList();

        for (Part p : allParts) {
            if (p.getName().contains(partName)) {
                namedPart.add(p);
            }
        }
        return namedPart;
    }

    /**
     * The method for looking up a Product in the allProducts observable list by productName.
     * This method iterates through the allProducts list and compares the passed in string with current Product object's productName.
     * If the string contains the full or partial productName it is added to a new observable list.
     *
     * @param productName the productName to look up
     * @return namedProduct observable list
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> namedProduct = FXCollections.observableArrayList();

        for (Product p : allProducts) {
            if (p.getName().contains(productName)) {
                namedProduct.add(p);
            }
        }
        return namedProduct;
    }

    /**
     * The method for updating a Part in the allParts observable list.
     * A Part object and its index are passed into the method to update the allParts observable list.
     *
     * @param index        the index of the Part to update
     * @param selectedPart the Part to update
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * The method for updating a Product in the allProducts observable list.
     * A Product object and its index are passed into the method to update the allProducts observable list.
     *
     * @param index           the index of the Product to update
     * @param selectedProduct the Product to update
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * The method for deleting a Part from the allParts observable list.
     * A Part object is passed into the method, if the Part is null the method return false otherwise the method removes it from allParts and returns true.
     *
     * @param selectedPart the Part to delete
     * @return false if the Part is null, returns true if the Part was deleted
     */
    public static boolean deletePart(Part selectedPart) {
        if (selectedPart == null)
            return false;
        else allParts.remove(selectedPart);
        return true;
    }

    /**
     * The method for deleting a Product from the allProducts observable list.
     * A Product object is passed into the method, if the Product is null the method return false otherwise the method removes it from allProducts and returns true.
     * If the Product has Parts in the associatedParts observable list the user is alerted and the product is not deleted.
     *
     * @param selectedProduct the Part to delete
     * @return false if the Part is null, returns true if the Product was deleted
     */
    public static boolean deleteProduct(Product selectedProduct) {
        Alert productDeleteError = new Alert(Alert.AlertType.ERROR);
        productDeleteError.setTitle("Product Delete Error");
        productDeleteError.setHeaderText("Selected Product unable to be deleted");
        productDeleteError.setContentText("Selected Product contains associated parts");

        if (selectedProduct == null) {
            return false;
        } else if (selectedProduct.getAssociatedParts().size() >= 1) {
            productDeleteError.showAndWait();
        } else allProducts.remove(selectedProduct);
        return true;
    }

    /**
     * The getter method for the allParts observable list.
     *
     * @return allParts observable list.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * The getter method for the allProducts observable list.
     *
     * @return allProducts observable list.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    static{
        addTestData();
    }

    public static void addTestData(){
        InHouse screw = new InHouse(uniquePartId,"screw", 0.12, 5, 1, 12, "200");
        main.Inventory.addPart(screw);
        uniquePartId++;
        Outsourced washer = new Outsourced(uniquePartId,"washer", 0.13, 5, 1, 12, "Company 1");
        main.Inventory.addPart(washer);
        uniquePartId++;
        Outsourced nut = new Outsourced(uniquePartId,"nut", 0.14, 5, 1, 12, "Company 2");
        main.Inventory.addPart(nut);
        uniquePartId++;

        Product trek = new Product(uniqueProductId,"Trek",105.00,2,2,5);
        main.Inventory.addProduct(trek);
        trek.addAssociatedPart(screw);
        uniqueProductId++;
        Product schwinn = new Product(uniqueProductId,"Schwinn",100.00,1,1,4);
        main.Inventory.addProduct(schwinn);
        schwinn.addAssociatedPart(washer);
        uniqueProductId++;
        Product giant = new Product(uniqueProductId,"Giant",101.00,5,0,6);
        main.Inventory.addProduct(giant);
        uniqueProductId++;
    }
}



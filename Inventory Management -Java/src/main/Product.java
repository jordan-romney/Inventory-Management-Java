package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class creates members and methods for Product objects.
 */
public class Product {
    private ObservableList<Part> associatedParts= FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    /**
     * The Product object Constructor
     * @param id the id to set
     * @param name the name to set
     * @param price the price to set
     * @param stock the stock level to set
     * @param min the min inventory level to set
     * @param max the max inventory level to set
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }
    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }
    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }
    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }
    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }
    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }
    /**
     * The method for adding Parts to the associatedParts observable list.
     * @param part
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    /**
     * The method for removing Parts from the associatedParts observable list.
     * The method checks if the list contains any Parts and removes the passed in Part.
     * @param selectedAssociatedPart
     * @return false if list is null or true if a part is removed
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if(associatedParts == null)
            return false;
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }
    /**
     * The getter method for the associatedParts observable list.
     * @return associatedPart observable list
     */
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }
}


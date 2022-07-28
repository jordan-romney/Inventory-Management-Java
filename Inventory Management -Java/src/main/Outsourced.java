package main;
/**
 * This class creates members and methods for Outsourced Part objects. This class inherits members and methods from the abstract Part class.
 */
public class Outsourced extends Part{
    private String companyName;
    /**
     * The Outsourced Part object constructor.
     * The constructor uses all parameters from the inherited Part class and then adds one more parameter of companyName.
     * @param id the id to set
     * @param name the name to set
     * @param price the price to set
     * @param stock the stock level to set
     * @param min the min inventory level to set
     * @param max the mac inventory level to set
     * @param companyName the companyName to set
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

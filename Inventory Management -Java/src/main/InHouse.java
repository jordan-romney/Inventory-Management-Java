package main;

/**
 * This class creates members and methods for InHouse Part objects. This class inherits members and methods from the abstract Part class.
 */
public class InHouse extends Part{
    private String machineId;

    /**
     * The InHouse Part object constructor.
     * The constructor uses all parameters from the inherited Part class and then adds one more parameter of machineID.
     * @param id the id to set
     * @param name the name to set
     * @param price the price to set
     * @param stock the stock level to set
     * @param min the min inventory level to set
     * @param max the max inventory level to set
     * @param machineId the machineId to set
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, String machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @return the machineId
     */
    public String getMachineId() {
        return machineId;
    }

    /**
     * @param machineId the machineId to set
     */
    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }
}

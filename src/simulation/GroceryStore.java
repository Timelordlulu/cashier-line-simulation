package simulation;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * The Simulation class for the Grocery Store.
 * It has a queue of customers representing all coming customers and a list of registers.
 */
public class GroceryStore {
    private static PriorityQueue<Customer> customers = new PriorityQueue<Customer>();
    private static ArrayList<Register> registers = new ArrayList<Register>();

    public GroceryStore(ArrayList<Register> registers, PriorityQueue<Customer> customers) {
        this.customers = customers;
        this.registers = registers;
    }

    public static ArrayList<Register> getRegisters() {
        return registers;
    }

    public static PriorityQueue<Customer> getCustomers() {
        return customers;
    }

    /**
     * update the status of the grocery store by updating all the registers
     * @param time the number of minutes passed after last update
     */
    public void updateStatus(int time) {
        for (Register r: registers) {
            r.updateStatus(time);
        }
    }
}
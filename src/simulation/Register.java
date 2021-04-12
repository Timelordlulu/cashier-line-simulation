package simulation;

import java.util.LinkedList;

/**
 * The class for Register. Parent class of TrainRegister.
 */
public class Register implements Comparable<Register> {
    protected int registerNum;
    protected int customerNum;
    protected LinkedList<Customer> customerLine;
    protected double processSpeed;

    public Register(int registerNum) {
        this.registerNum = registerNum;
        this.customerLine = new LinkedList<Customer>();
        this.customerNum = 0;
        this.processSpeed = 1.0;
    }

    public LinkedList<Customer> getCustomerLine() {
        return customerLine;
    }

    public int getCustomerNum() {
        return customerNum;
    }

    public double getProcessSpeed() {
        return processSpeed;
    }

    public void addCustomer(Customer c) {
        this.customerLine.add(c);
        this.customerNum += 1;
    }

    public void removeCustomer() {
        this.customerLine.remove();
        this.customerNum -= 1;
    }

    /**
     * update the customer line in the register.
     * @param time the number of minutes passed after last update
     */
    public void updateStatus(int time) {
        double itemProcessed = time / processSpeed;
        while (this.getCustomerNum() > 0) {
            Customer currentCustomer = this.getCustomerLine().peek();
            if (currentCustomer.getItemCnt() > itemProcessed) {
                currentCustomer.setItemCnt(currentCustomer.getItemCnt()-itemProcessed);
                return;
            } else {
                itemProcessed -= currentCustomer.getItemCnt();
                this.removeCustomer();
            }
        }
        return;
    }

    @Override
    /**
     * Compare which register has the shorter line.
     */
    public int compareTo(Register o) {
        if (this.customerNum < o.customerNum) {
            return -1;
        } else if (this.customerNum > o.customerNum) {
            return 1;
        } else {
            return 0;
        }
    }
}

package tests;
import simulation.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationTest {
    private ArrayList<Register> initializeRegisters() {
        Register r1 = new Register(1);
        Register r2 = new TrainRegister(2);
        ArrayList<Register> registers = new ArrayList<Register>();
        registers.add(r1);
        registers.add(r2);
        return registers;
    }

    @Test
    public void testChooseRegisterEasy() {
        ArrayList<Register> registers = initializeRegisters();
        Register r1 = registers.get(0);
        Register r2 = registers.get(1);
        r1.addCustomer(new Customer(Customer.Type.A, 0,3));
        r2.addCustomer(new Customer(Customer.Type.A, 0,1));
        r2.addCustomer(new Customer(Customer.Type.A, 0,2));
        Customer customerA = new Customer(Customer.Type.A, 1, 1);
        assertEquals(r1, customerA.chooseRegister(registers));
        Customer customerB = new Customer(Customer.Type.B, 2, 1);
        assertEquals(r2, customerB.chooseRegister(registers));
    }

    @Test
    public void testChooseRegisterEmpty() {
        ArrayList<Register> registers = initializeRegisters();
        Register r1 = registers.get(0);
        Register r2 = registers.get(1);
        r1.addCustomer(new Customer(Customer.Type.A, 0,3));
        Customer customerA = new Customer(Customer.Type.A, 1, 1);
        assertEquals(r2, customerA.chooseRegister(registers));
        Customer customerB = new Customer(Customer.Type.B, 2, 1);
        assertEquals(r2, customerB.chooseRegister(registers));
    }

    @Test
    public void testChooseRegisterEqual() {
        ArrayList<Register> registers = initializeRegisters();
        Register r1 = registers.get(0);
        Register r2 = registers.get(1);
        r1.addCustomer(new Customer(Customer.Type.A, 0,1));
        r2.addCustomer(new Customer(Customer.Type.A, 0,1));
        Customer customerA = new Customer(Customer.Type.A, 1, 1);
        assertEquals(r1, customerA.chooseRegister(registers));
        Customer customerB = new Customer(Customer.Type.B, 1, 1);
        assertEquals(r1, customerB.chooseRegister(registers));
    }

    @Test
    public void testCompareCustomerEasy() {
        Customer customerA = new Customer(Customer.Type.A, 2, 1);
        Customer customerB = new Customer(Customer.Type.B, 3, 1);
        assertEquals(-1, customerA.compareTo(customerB));
    }

    @Test
    public void testCompareCustomerEqual() {
        Customer customerA = new Customer(Customer.Type.A, 3, 3);
        Customer customerB = new Customer(Customer.Type.B, 3, 2);
        assertEquals(1, customerA.compareTo(customerB));
    }

    @Test
    public void testCompareCustomerType() {
        Customer customerA = new Customer(Customer.Type.A, 4, 1);
        Customer customerB = new Customer(Customer.Type.B, 4, 1);
        assertEquals(-1, customerA.compareTo(customerB));
    }

    @Test
    public void testUpdateStatusEasy() {
        Register r1 = new Register(1);
        r1.addCustomer(new Customer(Customer.Type.A, 1,3));
        r1.updateStatus(2);
        assertEquals(1.0, r1.getCustomerLine().peek().getItemCnt());
    }

    @Test
    public void testUpdateStatusEqual() {
        Register r1 = new Register(1);
        r1.addCustomer(new Customer(Customer.Type.A, 1,3));
        r1.updateStatus(3);
        assertEquals(0, r1.getCustomerNum());
    }

    @Test
    public void testUpdateStatusLarge() {
        Register r1 = new Register(1);
        r1.addCustomer(new Customer(Customer.Type.A, 1,3));
        r1.addCustomer(new Customer(Customer.Type.A, 2,4));
        r1.updateStatus(5);
        assertEquals(2.0, r1.getCustomerLine().peek().getItemCnt());
    }
}

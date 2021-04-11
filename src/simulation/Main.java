package simulation;

import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) {
        // Handle the input and initialize the GroceryStore.
        File file = new File(args[0]);
        String readLine = "";
        boolean firstLine = true;
        BufferedReader br = null;
        ArrayList<Register> registers = new ArrayList<Register>();
        PriorityQueue<Customer> customers = new PriorityQueue<Customer>();
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.err.println("Cannot open the file.");
            System.exit(1);
        }
        try {
            while ((readLine = br.readLine()) != null) {
                if (firstLine == true) {
                    int n = Integer.parseInt(readLine);
                    for (int i = 1; i <= n-1; i++) {
                        Register r = new Register(i);
                        registers.add(r);
                    }
                    Register r = new TrainRegister(n);
                    registers.add(r);
                    firstLine = false;
                } else {
                    customers.add(addCustomer(readLine));
                }
            }
            GroceryStore groceryStore = new GroceryStore(registers, customers);
            // Calculate the time
            int finishedTime = checkout(groceryStore);
            // Output the result
            System.out.println("Finished at: t=" + finishedTime + " minutes");
        } catch (IOException e) {
            System.err.println("Failed to read the file");
            System.exit(1);
        }
    }

    /**
     * helper function to turn the input string into a Customer Object.
     * @param line input string per line
     * @return new Customer Object
     */
    public static Customer addCustomer(String line) {
        String[] lineList = line.split(" ");
        if (lineList.length == 3) {
            if (lineList[0].equals("A")) {
                return new Customer(Customer.Type.A, Integer.parseInt(lineList[1]), Integer.parseInt(lineList[2]));
            } else if (lineList[0].equals("B")) {
                return new Customer(Customer.Type.B, Integer.parseInt(lineList[1]), Integer.parseInt(lineList[2]));
            } else {
                System.err.println("Wrong Customer type");
                System.exit(1);
                return null;
            }
        } else {
            System.err.println("wrong input format");
            System.exit(1);
            return null;
        }
    }

    /**
     * helper function to calculate the total check out time.
     * @param groceryStore GroceryStore Object
     * @return finished time
     */
    public static int checkout(GroceryStore groceryStore) {
        // Calculate the time when all the customers go to a register.
        ArrayList<Register> registers = groceryStore.getRegisters();
        PriorityQueue<Customer> customers = groceryStore.getCustomers();
        int currentTime = 0;
        while (!customers.isEmpty()) {
            Customer c = customers.poll();
            groceryStore.updateStatus(c.getArriveTime() - currentTime);
            currentTime = c.getArriveTime();
            Register targetRegister = c.chooseRegister(registers);
            targetRegister.addCustomer(c);
        }
        // After all the customers are in the cashier, calculate the remaining checkout time.
        int maxCheckedOutTime = 0;
        for (Register r: registers) {
            int checkedOutTime = 0;
            for (Customer c: r.getCustomerLine()) {
                checkedOutTime += c.getItemCnt() * r.getProcessSpeed();
            }
            if (checkedOutTime > maxCheckedOutTime) {
                maxCheckedOutTime = checkedOutTime;
            }
        }
        return currentTime + maxCheckedOutTime;
    }
}

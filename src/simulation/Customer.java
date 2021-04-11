package simulation;

import java.util.ArrayList;
import java.util.Collections;

public class Customer implements Comparable<Customer> {
    private double itemCnt;
    private int arriveTime;
    private Type type;


    public Customer(Type type, int arriveTime, int itemCnt) {
        this.type = type;
        this.arriveTime = arriveTime;
        this.itemCnt = itemCnt;
    }

    public Type getType() {
        return type;
    }

    public int getArriveTime() {
        return arriveTime;
    }

    public double getItemCnt() {
        return itemCnt;
    }

    public void setItemCnt(double itemCnt) {
        this.itemCnt = itemCnt;
    }

    /**
     * For each type of customer, choose the register he will go to.
     * @param registers list of registers.
     * @return the register he will go to.
     */
    public Register chooseRegister(ArrayList<Register> registers) {
        // No matter customer type A or B, choose the first register that has an empty line.
        for (int i = 0; i < registers.size(); i++) {
            if (registers.get(i).getCustomerNum() == 0) {
                return registers.get(i);
            }
        }
        if (this.type.typeValue == 0) {
            // Customer type A choose the smallest line.
            return Collections.min(registers);
        } else {
            // Customer type B choose the line that the last customer has the fewest item.
            Register targetRegister = registers.get(0);
            int minLastCustomerItem = (int) Math.ceil(targetRegister.getCustomerLine().peekLast().getItemCnt());
            for (int i = 1; i < registers.size(); i++) {
                Register currentRegister = registers.get(i);
                int currentLastCustomerItem = (int) Math.ceil(currentRegister.getCustomerLine().peekLast().getItemCnt());
                if (currentLastCustomerItem < minLastCustomerItem) {
                    targetRegister = currentRegister;
                    minLastCustomerItem = currentLastCustomerItem;
                }
            }
            return targetRegister;
        }
    }

    @Override
    /**
     * Decide which customer choose first.
     */
    public int compareTo(Customer o) {
        if (this.arriveTime < o.arriveTime) {
            return -1;
        }
        else if (this.arriveTime > o.arriveTime) {
            return 1;
        }
        if (this.itemCnt < o.itemCnt) {
            return -1;
        }
        else if (this.itemCnt > o.itemCnt) {
            return 1;
        }
        if (this.type.typeValue < o.type.typeValue) {
            return -1;
        }
        else if (this.type.typeValue > o.type.typeValue) {
            return 1;
        }
        return 0;
    }

    public enum Type {
        A(0), B(1);
        public final int typeValue;
        private Type(int typeValue) {
            this.typeValue = typeValue;
        }
    }
}


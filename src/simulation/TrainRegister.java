package simulation;

/**
 * the class for training register, subclass of Register.
 */
public class TrainRegister extends Register {
    public TrainRegister(int registerNum) {
        super(registerNum);
        this.processSpeed = 2.0;
    }
}

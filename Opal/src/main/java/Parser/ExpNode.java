package Parser;

public abstract class ExpNode {

    public static final int VARIABLE_NODE = 1;
    public static final int CONSTANT_NODE = 2;
    public static final int ADDITION_NODE = 3;
    public static final int MULTIPLICATION_NODE = 4;
    public abstract int getType();
    public abstract double getValue();

}

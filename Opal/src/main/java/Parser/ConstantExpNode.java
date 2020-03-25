package Parser;


public class ConstantExpNode extends ExpNode {

    private double value;

    public ConstantExpNode(double value) {
        this.value = value;
    }

    public ConstantExpNode(String value) {
        this.value = Double.valueOf(value);
    }
    @Override
    public double getValue() {
        return value;
    }
    @Override
    public int getType() {
        return ExpNode.CONSTANT_NODE;
    }
}

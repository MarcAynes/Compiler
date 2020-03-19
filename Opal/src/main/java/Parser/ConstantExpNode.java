package Parser;

import com.sun.org.apache.xpath.internal.ExpressionNode;

public class ConstantExpNode extends ExpNode {

    private double value;

    public ConstantExpNode(double value) {
        this.value = value;
    }

    public ConstantExpNode(String value) {
        this.value = Double.valueOf(value);
    }

    public double getValue() {
        return value;
    }

    public int getType() {
        return ExpNode.CONSTANT_NODE;
    }
}

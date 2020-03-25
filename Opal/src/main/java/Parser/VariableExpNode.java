package Parser;

import org.w3c.dom.events.EventException;
import org.w3c.dom.events.EventListener;

public class VariableExpNode extends ExpNode {

    private String name;
    private double value;
    private boolean valueSet;

    public VariableExpNode(String name) {
        this.name = name;
        valueSet = false;
    }

    public int getType() {
        return ExpNode.VARIABLE_NODE;
    }

    public void setValue(double value) {
        this.value = value;
        this.valueSet = true;
    }

    public double getValue() {
        if (valueSet)
            return value;
        else {
            try {
                throw new Exception("Variable " + name + " was not initialized.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}

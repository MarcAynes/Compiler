package Parser;

public class MultiplicationExpNode extends SequenceExpNode{

    public MultiplicationExpNode() {
        super();
    }

    public MultiplicationExpNode(ExpNode a, boolean positive) {
        super(a, positive);
    }

    @Override
    public int getType() {
        return ExpNode.MULTIPLICATION_NODE;
    }

    @Override
    public double getValue() {
        double prod = 1.0;
        for (Term t : terms) {
            if (t.positive)
                prod *= t.expression.getValue();
            else
                prod /= t.expression.getValue();
        }
        return prod;
    }
}
